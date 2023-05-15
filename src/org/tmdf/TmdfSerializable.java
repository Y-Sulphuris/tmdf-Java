package org.tmdf;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public interface TmdfSerializable extends ConvertibleToTagEx {
    @Override
    default TagMap toTag() {
        TagMap map = new TagMap();
        Class<?> currentClass = getClass();
        do {
            for (Field field : currentClass.getDeclaredFields()) {
                String tmdfName = TmdfSerializationHelper.getTmdfNameAnnotation(field);
                if (tmdfName != null) {
                    field.setAccessible(true);
                    int modifiers = field.getModifiers();
                    if (Modifier.isTransient(modifiers) || Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers))
                        throw new IllegalStateException("Unsupported modifiers: " + field.toGenericString());
                    Object fieldValue;
                    try {
                        fieldValue = field.get(this);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    Tag<?> tag = null;
                    if (fieldValue instanceof ConvertibleToTagEx) tag = ((ConvertibleToTagEx) fieldValue).toTag();
                    else tag = Tag.wrap(fieldValue);

                    if (tag != null) map.put(tmdfName, tag);
                }
            }
            currentClass = currentClass.getSuperclass();
        } while (currentClass != Object.class && currentClass != null);
        return map;
    }
    @Override
    default void load(TagMap map) {
        Class<?> currentClass = getClass();
        do {
            for (Field field : currentClass.getDeclaredFields()) {
                String tmdfName = TmdfSerializationHelper.getTmdfNameAnnotation(field);
                if (tmdfName != null) {
                    field.setAccessible(true);
                    int modifiers = field.getModifiers();
                    if (Modifier.isTransient(modifiers) || Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers))
                        throw new IllegalStateException("Unsupported modifiers: " + field.toGenericString());

                    Tag<?> tag = map.get(tmdfName);
                    if (tag != null) {
                        if (TmdfSerializationHelper.isImplements(field.getType(), ConvertibleToTagEx.class)) {
                            try {
                                ((ConvertibleToTagEx)field.get(this)).load((TagMap) tag);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (TmdfSerializationHelper.isInstance(field.getType(),Tag.class)) {
                            try {
                                field.set(this,tag);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                System.out.println(field.getType() + " " + Arrays.toString(field.getType().getInterfaces()));
                                System.out.println(TmdfSerializationHelper.isInstance(TmdfSerializable.class, ConvertibleToTagEx.class));
                                if (tag instanceof NumTag<?>) {
                                    if (field.getType() == byte.class) {
                                        field.set(this,((NumTag<?>) tag).byteValue());
                                    }
                                    if (field.getType() == int.class) {
                                        field.set(this,((NumTag<?>) tag).intValue());
                                    }
                                    if (field.getType() == short.class) {
                                        field.set(this,((NumTag<?>) tag).shortValue());
                                    }
                                    if (field.getType() == long.class) {
                                        field.set(this,((NumTag<?>) tag).longValue());
                                    }
                                } else field.set(this,tag.getValue());
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                }
            }
            currentClass = currentClass.getSuperclass();
        } while (currentClass != Object.class && currentClass != null);
    }
}
class TmdfSerializationHelper {
    private TmdfSerializationHelper() {
        throw new UnsupportedOperationException();
    }
    static String getTmdfNameAnnotation(Field field) {
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof TmdfName) {
                return ((TmdfName) annotation).value();
            }
        }
        return null;
    }
    static boolean isInstance(Class<?> cls, final Class<?> target) {
        if (cls.isInterface()) {
            return isImplements(cls,target);
        } else {
            while (true) {
                if (cls == target) return true;
                if (cls == null) return false;
                cls = cls.getSuperclass();
            }
        }
    }
    static boolean isImplements(Class<?> cls, Class<?> interf) {
        while (true) {
            if (cls == null || cls == Object.class) return false;
            Class<?>[] interfaces = cls.getInterfaces();
            if (contains(interfaces,interf)) return true;
            cls = cls.getSuperclass();
        }
    }
    static boolean contains(Class<?>[] container, Class<?> req) {
        for (Class<?> obj : container) {
            if (isInstance(obj,req)) return true;
        }
        return false;
    }
}
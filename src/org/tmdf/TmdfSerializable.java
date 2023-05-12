package org.tmdf;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public interface TmdfSerializable extends ConvertibleToTag {
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
                    if (fieldValue instanceof ConvertibleToTag) tag = ((ConvertibleToTag) fieldValue).toTag();
                    else tag = Tag.wrap(fieldValue);

                    if (tag != null) map.put(tmdfName, tag);
                }
            }
            currentClass = currentClass.getSuperclass();
        } while (currentClass != Object.class && currentClass != null);
        return map;
    }
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
                        try {
                            field.set(this,tag.getValue());// FIXME: 12.05.2023 problematic frame (cant load custom tmdf serializable classes)
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
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
}
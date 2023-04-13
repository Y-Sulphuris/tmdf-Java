package org.tmdf.debug;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.tmdf.BoolArrayTag;
import org.tmdf.Tag;

public final class Debugger {
    @Deprecated
    public static long heapSizeOf(Tag<?> tag) throws NoClassDefFoundError {
        return ObjectSizeCalculator.getObjectSize(tag);
    }
    @Deprecated
    public static long heapSizeOf(Tag<?> tag, String name) throws NoClassDefFoundError{
        return ObjectSizeCalculator.getObjectSize(tag) + ObjectSizeCalculator.getObjectSize(name);
    }
    public static long binarySizeOf(Tag<?> tag) {
        return tag.tagSize(null);
    }
    public static String toBinaryString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        BoolArrayTag bits = BoolArrayTag.of(bytes);
        for (int i = 0; i < bits.length(); i++) {
            if (i % 8 == 0 && i != 0) builder.append('_');
            if (i % 64 == 0 && i != 0) builder.append("__");
            builder.append(bits.get(i)?1:0);
        }
        return builder.toString();
    }
}

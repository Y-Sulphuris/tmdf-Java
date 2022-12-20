package org.tmdf.debug;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.tmdf.Tag;

public final class Debugger {
    public static long heapSizeOf(Tag<?> tag) {
        return ObjectSizeCalculator.getObjectSize(tag);
    }
    public static long heapSizeOf(Tag<?> tag, String name) {
        return ObjectSizeCalculator.getObjectSize(tag) + ObjectSizeCalculator.getObjectSize(name);
    }
    public static long binarySizeOf(Tag<?> tag) {
        return tag.tagSize(null);
    }
}

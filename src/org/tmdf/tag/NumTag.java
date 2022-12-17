package org.tmdf.tag;

public abstract class NumTag<T extends Number> extends Tag<T> implements Comparable<NumTag<T>> {
	public final int intValue() {
		return getValue().intValue();
	}

	public final long longValue() {
		return getValue().longValue();
	}

	public final float floatValue() {
		return getValue().floatValue();
	}

	public final double doubleValue() {
		return getValue().doubleValue();
	}

	public final byte byteValue() {
		return getValue().byteValue();
	}

	public final short shortValue() {
		return getValue().shortValue();
	}

	public final boolean isDecimal() {
		return getValue() instanceof Double || getValue() instanceof Float;
	}

}

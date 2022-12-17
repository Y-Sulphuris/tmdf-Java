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

	@Override
	public final int payloadSize() {
		if (this instanceof ByteTag) return 1;
		if (this instanceof ShortTag) return 2;
		if (this instanceof IntTag) return 4;
		if (this instanceof FloatTag) return 4;
		if (this instanceof LongTag) return 8;
		if (this instanceof DoubleTag) return 8;
		throw new UnknownTagException(this.toString());
	}
}

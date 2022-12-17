package org.tmdf.tag;

import static org.tmdf.ByteBuffersCache.bb8;

public final class DoubleTag extends NumTag<Double> {
	private double value = 0.0;
	public DoubleTag(double value) {
		this.value = value;
	}
	public DoubleTag() {

	}

	@Override
	public int compareTo(NumTag<Double> o) {
		return Double.compare(value,o.getValue());
	}

	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public Tag<Double> clone() {
		return new DoubleTag(value);
	}

	@Override
	protected byte[] getPayload() {
		bb8.putDouble(0,value);
		return new byte[]{
			bb8.get(0),
			bb8.get(1),
			bb8.get(2),
			bb8.get(3),
			bb8.get(4),
			bb8.get(5),
			bb8.get(6),
			bb8.get(7),
		};
	}


	public boolean isNaN() {
		return getValue().isNaN();
	}

	public boolean isInfinite() {
		return getValue().isInfinite();
	}

	public int compareTo(Double anotherDouble) {
		return getValue().compareTo(anotherDouble);
	}



	public static final DoubleTag POSITIVE_INFINITY = new DoubleTag(Double.POSITIVE_INFINITY);


	public static final DoubleTag NEGATIVE_INFINITY = new DoubleTag(Double.NEGATIVE_INFINITY);


	public static final DoubleTag NaN = new DoubleTag(Double.NaN);
}

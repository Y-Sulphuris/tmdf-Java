package org.tmdf;

import static org.tmdf.ByteBuffersCache.bb4;

/**
 * 	FloatTag: 5<br>
 * 	Payload: four-byte fractional value (4 bytes) - IEEE 754-2008<br>
 * 	Flag: none
 */
public final class FloatTag extends NumTag<Float> {
	private float value = 0.0f;
	public FloatTag(float value) {
		this.value = value;
	}
	public FloatTag() {}

	@Override
	public int compareTo(NumTag<Float> o) {
		return Float.compare(value,o.getValue());
	}

	@Override
	public Float getValue() {
		return value;
	}

	@Override
	public void setValue(Float value) {
		this.value = value;
	}


	@Override
	protected byte[] getPayload() {
		bb4.putFloat(0,value);
		return new byte[]{bb4.get(0),bb4.get(1),bb4.get(2),bb4.get(3)};
	}


	public boolean isNaN() {
		return getValue().isNaN();
	}

	public boolean isInfinite() {
		return getValue().isInfinite();
	}

	public int compareTo(Float anotherFloat) {
		return getValue().compareTo(anotherFloat);
	}



	public static final FloatTag POSITIVE_INFINITY = new FloatTag(Float.POSITIVE_INFINITY);


	public static final FloatTag NEGATIVE_INFINITY = new FloatTag(Float.NEGATIVE_INFINITY);


	public static final FloatTag NaN = new FloatTag(Float.NaN);
}

package org.tmdf;


import static org.tmdf.ByteBuffersCache.bb2;
/**
 * 	ShortTag: 2<br>
 * 	Payload: two-byte integer value (2 bytes)<br>
 * 	Flag: true if unsigned, false if not
 */
public final class ShortTag extends NumTag<Integer> {
	private short value = 0;
	public ShortTag(short value) {
		this.value = value;
	}
	public ShortTag() {}

	@Override
	public int compareTo(NumTag<Integer> o) {
		return getValue().compareTo(o.getValue());
	}

	@Override
	public Integer getValue() {
		return isSigned() ? (int)value : Short.toUnsignedInt(value);
	}

	@Override
	public void setValue(Integer value) {
		if (value.shortValue() != value) {
			throw new ArithmeticException("short overflow");
		}
		this.value = value.shortValue();
	}

	@Override
	public ShortTag clone() {
		return (ShortTag) new ShortTag(value).setFlag(getFlag());
	}

	@Override
	protected byte[] getPayload() {
		//bb.order(ByteOrder.BIG_ENDIAN);
		bb2.putShort(0,value);
		return new byte[]{bb2.get(0), bb2.get(1)};
	}

	public int compareTo(Integer anotherShort) {
		return getValue().compareTo(anotherShort);
	}
}

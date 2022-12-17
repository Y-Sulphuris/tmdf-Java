package org.tmdf.tag;


import static org.tmdf.ByteBuffersCache.bb2;

public final class ShortTag extends NumTag<Short> {
	private short value = 0;
	public ShortTag(short value) {
		this.value = value;
	}
	public ShortTag() {}

	@Override
	public int compareTo(NumTag<Short> o) {
		return getValue().compareTo(o.getValue());
	}

	@Override
	public Short getValue() {
		return value;
	}

	@Override
	public void setValue(Short value) {
		this.value = value;
	}

	@Override
	public Tag<Short> clone() {
		return new ShortTag(value);
	}

	@Override
	protected byte[] getPayload() {
		//bb.order(ByteOrder.BIG_ENDIAN);
		bb2.putShort(0,value);
		return new byte[]{bb2.get(0), bb2.get(1)};
	}

	public int compareTo(Short anotherShort) {
		return getValue().compareTo(anotherShort);
	}
}

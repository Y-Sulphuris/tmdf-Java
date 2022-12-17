package org.tmdf.tag;

public final class ByteTag extends NumTag<Byte> {
	private byte value = 0;
	public ByteTag(byte value) {
		this.value = value;
	}
	public ByteTag() {}

	@Override
	public int compareTo(NumTag<Byte> o) {
		return getValue().compareTo(o.getValue());
	}

	@Override
	public Byte getValue() {
		return value;
	}

	@Override
	public void setValue(Byte value) {
		this.value = value;
	}

	@Override
	public ByteTag clone() {
		return new ByteTag(value);
	}

	@Override
	protected byte[] getPayload() {
		return new byte[]{value};
	}

	public int compareTo(Byte anotherByte) {
		return getValue().compareTo(anotherByte);
	}
}

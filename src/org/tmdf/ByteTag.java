package org.tmdf;

/**
 * 	ByteTag: 1<br>
 * 	Payload: single-byte integer value (1 byte)<br>
 * 	Flag: true if unsigned, false if not
 */
public final class ByteTag extends NumTag<Integer> {
	private byte value = 0;
	public ByteTag(byte value) {
		this.value = value;
	}
	public ByteTag(int value) {
		this(value,value < 0); //if value is positive, set tag to unsigned as default (flag: 1)
	}
	public ByteTag(int value, boolean signed) {
		setSigned(signed);
		setValue(value);
	}

	@Override
	public int compareTo(NumTag<Integer> o) {
		return getValue().compareTo(o.getValue());
	}

	@Override
	public Integer getValue() {
		return isSigned() ? (int)value : Byte.toUnsignedInt(value);
	}

	@Override
	public void setValue(Integer value) {
		if (isSigned()) {
			if (value > Byte.MAX_VALUE || value < Byte.MIN_VALUE)
				throw new ArithmeticException("byte overflow");
		} else {
			if (value > 255 || value < 0)
				throw new ArithmeticException("byte overflow");
		}
		this.value = value.byteValue();
	}


	@Override
	protected byte[] getPayload() {
		return new byte[]{value};
	}

	public int compareTo(Integer anotherByte) {
		return getValue().compareTo(anotherByte);
	}
}

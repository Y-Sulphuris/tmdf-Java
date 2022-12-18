package org.tmdf;

import java.util.Arrays;

/**
 * Type: 11<br>
 * Payload: an ordered array of one-byte integers. The first 4 bytes mean the length of the array (size in bytes equals array size + 4)
 */
public final class ByteArrayTag extends NumArrayTag<byte[],Byte> {
	private byte[] value;
	public ByteArrayTag(byte[] value) {
		this.value = value;
	}
	public ByteArrayTag(int length) {
		this.value = new byte[length];
	}
	@Override
	public byte[] getValue() {
		return value;
	}

	@Override
	public void setValue(byte[] value) {
		this.value = value;
	}

	@Override
	public ByteArrayTag clone() {
		return new ByteArrayTag(value.clone());
	}

	@Override
	protected byte[] getPayload() {
		return TmdfUtils.sum(TmdfUtils.intToByteArray(value.length),value);
	}

	@Override
	public Byte get(int index) {
		return value[index];
	}

	@Override
	public void set(int index, Byte x) {
		this.value[index] = x;
	}

	@Override
	public int length() {
		return value.length;
	}



	@Override
	public String toString() {
		return isSigned() ? Arrays.toString(value) : toUnsignedString();
	}

	@Override
	public int payloadSize() {
		return 4*length();
	}

	private String toUnsignedString() {
		if (value == null)
			return "null";
		int iMax = value.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0; ; i++) {
			b.append(Byte.toUnsignedInt(value[i]));
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
}

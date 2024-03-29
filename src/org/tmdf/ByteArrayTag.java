package org.tmdf;

import java.util.Arrays;

/**
 * 	ByteArrayTag: 11<br>
 * 	Payload: an ordered array of one-byte integers. The first 4 bytes mean the length of the array (size in bytes = array size + 4)<br>
 * 	Flag: If true, all values are unsigned, else all values are signed
 */
public final class ByteArrayTag extends NumArrayTag<byte[],Byte> {
	public static ByteArrayTag of(byte... bytes) {
		return new ByteArrayTag(bytes);
	}
	public static ByteArrayTag of(int... numbers) {
		byte[] bytes = new byte[numbers.length];
		for (int i = 0; i < numbers.length; i++) {
			bytes[i] = (byte) numbers[i];
		}
		return new ByteArrayTag(bytes);
	}

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


	public String toContentString() {
		return isSigned() ? Arrays.toString(value) : toUnsignedString();
	}

	@Override
	public int payloadSize() {
		return 4+length();
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

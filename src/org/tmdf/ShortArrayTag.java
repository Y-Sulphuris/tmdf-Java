package org.tmdf;

import java.util.Arrays;

import static org.tmdf.ByteBuffersCache.bb2;

/**
 * 	ShortArrayTag: 12<br>
 * 	Payload: an ordered array of two-byte integers. The first 4 bytes mean the length of the array (size in bytes = array size * 2 + 4)<br>
 * 	Flag: If true, all values are unsigned, else all values are signed
 */
public final class ShortArrayTag extends NumArrayTag<short[], Short> {
	private short[] value;

	public ShortArrayTag(short[] value) {
		this.value = value;
	}

	public ShortArrayTag(int length) {
		this.value = new short[length];
	}
	@Override
	public Short get(int index) {
		return value[index];
	}

	@Override
	public void set(int index, Short x) {
		value[index] = x;
	}

	@Override
	public int length() {
		return value.length;
	}

	@Override
	public short[] getValue() {
		return value;
	}

	@Override
	public void setValue(short[] value) {
		this.value = value;
	}


	@Override
	protected byte[] getPayload() {
		byte[] bytes = new byte[value.length*2+4];
		{
			byte[] intArrayLength = TmdfUtils.intToByteArray(value.length);
			System.arraycopy(intArrayLength, 0, bytes, 0, 4);
		}
		for (int i = 0; i < value.length; i++) {
			bb2.putShort(0,value[i]);
			bytes[4+i*2] = bb2.get(0);
			bytes[4+i*2+1] = bb2.get(1);
		}
		return bytes;
	}


	@Override
	public String toContentString() {
		return isSigned() ? Arrays.toString(value) : toUnsignedString();
	}

	@Override
	public int payloadSize() {
		return 4+length()*2;
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
			b.append(Integer.toUnsignedLong(value[i]));
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
}

package org.tmdf;

import java.util.Arrays;

import static org.tmdf.ByteBuffersCache.*;

/**
 * 	LongArrayTag: 14<br>
 * 	Payload: an ordered array of eight-byte integers. The first 4 bytes mean the length of the array (size in bytes = array size * 8 + 4)<br>
 * 	Flag: If true, all values are unsigned, else all values are signed
 */
public final class LongArrayTag extends NumArrayTag<long[],Long> {
	private long[] value;

	public LongArrayTag(long... value) {
		this.value = value;
	}
	public LongArrayTag(int length) {
		this.value = new long[length];
	}

	@Override
	public Long get(int index) {
		return value[index];
	}

	@Override
	public void set(int index, Long x) {
		value[index] = x;
	}

	@Override
	public int length() {
		return value.length;
	}

	@Override
	public long[] getValue() {
		return value;
	}

	@Override
	public void setValue(long[] value) {
		this.value = value;
	}


	@Override
	protected byte[] getPayload() {
		byte[] bytes = new byte[value.length*8+4];
		{
			byte[] intArrayLength = TmdfUtils.intToByteArray(value.length);
			System.arraycopy(intArrayLength, 0, bytes, 0, 4);
		}
		for (int i = 0; i < value.length; i++) {
			bb8.putLong(0,value[i]);
			for (int j = 0; j < 8; j++) {
				bytes[4+i*4+j] = bb8.get(j);
			}
		}
		return bytes;
	}


	@Override
	public String toContentString() {
		return isSigned() ? Arrays.toString(value) : toUnsignedString();
	}

	@Override
	public int payloadSize() {
		return 4+length()*8;
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
			b.append(Long.toUnsignedString(value[i]));
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}
}

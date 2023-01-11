package org.tmdf;

import java.util.Arrays;

import static org.tmdf.ByteBuffersCache.bb4;

/**
 * 	IntArrayTag: 13<br>
 * 	Payload: an ordered array of four-byte integers. The first 4 bytes mean the length of the array (size in bytes = array size * 4 + 4)<br>
 * 	Flag: If true, all values are unsigned, else all values are signed
 */
public final class IntArrayTag extends NumArrayTag<int[],Integer> {
	private int[] value;

	public IntArrayTag(int[] value) {
		this.value = value;
	}
	public IntArrayTag(int length) {
		value = new int[length];
	}

	public static IntArrayTag of(int... elements) {
		return new IntArrayTag(elements);
	}

	@Override
	public Integer get(int index) {
		return value[index];
	}

	@Override
	public void set(int index, Integer x) {
		value[index] = x;
	}

	@Override
	public int length() {
		return value.length;
	}

	@Override
	public int[] getValue() {
		return value;
	}

	@Override
	public void setValue(int[] value) {
		this.value = value;
	}

	@Override
	public IntArrayTag clone() {
		return new IntArrayTag(value.clone());
	}

	@Override
	protected byte[] getPayload() {
		byte[] bytes = new byte[value.length*4+4];
		{
			byte[] intArrayLength = TmdfUtils.intToByteArray(value.length);
			System.arraycopy(intArrayLength, 0, bytes, 0, 4);
		}
		for (int i = 0; i < value.length; i++) {
			bb4.putInt(0,value[i]);
			for (int j = 0; j < 4; j++) {
				bytes[4+i*4+j] = bb4.get(j);
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
		return 4+length()*4;
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

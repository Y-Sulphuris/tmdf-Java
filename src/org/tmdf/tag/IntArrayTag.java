package org.tmdf.tag;

import org.tmdf.TmdfUtils;

import java.util.Arrays;

import static org.tmdf.ByteBuffersCache.bb4;

public final class IntArrayTag extends ArrayTag<int[],Integer> {
	private int[] value;

	public IntArrayTag(int... value) {
		this.value = value;
	}
	public IntArrayTag(int length) {
		value = new int[length];
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
		return new IntArrayTag(value);
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
	public String toString() {
		return Arrays.toString(value);
	}

	@Override
	public int payloadSize() {
		return 4+length()*4;
	}
}

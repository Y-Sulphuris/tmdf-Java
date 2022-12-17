package org.tmdf.tag;

import org.tmdf.TmdfUtils;

import java.util.Arrays;

import static org.tmdf.ByteBuffersCache.*;

public final class LongArrayTag extends ArrayTag<long[],Long> {
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
	public LongArrayTag clone() {
		return new LongArrayTag(value);
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
	public String toString() {
		return Arrays.toString(value);
	}
}

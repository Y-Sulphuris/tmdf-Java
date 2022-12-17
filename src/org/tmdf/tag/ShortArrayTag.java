package org.tmdf.tag;

import org.tmdf.TmdfUtils;

import java.util.Arrays;

import static org.tmdf.ByteBuffersCache.bb2;

public final class ShortArrayTag extends ArrayTag<short[], Short> {
	private short[] value;

	public ShortArrayTag(short... value) {
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
	public ShortArrayTag clone() {
		return new ShortArrayTag(value);
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
	public String toString() {
		return Arrays.toString(value);
	}

}

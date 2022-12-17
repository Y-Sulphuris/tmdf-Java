package org.tmdf.tag;

import org.tmdf.TmdfUtils;

import java.util.Arrays;

public final class BooleanArrayTag extends ArrayTag<boolean[],Boolean> {

	private boolean[] value;

	public BooleanArrayTag(boolean... value) {
		this.value = value;
	}
	public BooleanArrayTag(int length) {
		this.value = new boolean[length];
	}



	@Override
	public Boolean get(int index) {
		return value[index];
	}

	@Override
	public void set(int index, Boolean x) {
		value[index] = x;
	}

	@Override
	public int length() {
		return value.length;
	}

	@Override
	public boolean[] getValue() {
		return value;
	}

	@Override
	public void setValue(boolean[] value) {
		this.value = value;
	}

	@Override
	public BooleanArrayTag clone() {
		return new BooleanArrayTag(value);
	}

	@Override
	protected byte[] getPayload() {
		byte[] bytes = new byte[value.length+4];
		{
			byte[] intArrayLength = TmdfUtils.intToByteArray(value.length);
			System.arraycopy(intArrayLength, 0, bytes, 0, 4);
		}
		for (int i = 0; i < value.length; i++) {
			bytes[i] = (byte) (value[i]?1:0);
		}
		return bytes;
	}


	@Override
	public String toString() {
		return Arrays.toString(value);
	}

	@Override
	public int payloadSize() {
		return 4+length();
	}
}

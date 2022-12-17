package org.tmdf.tag;

import org.tmdf.TmdfUtils;

import java.util.Arrays;

public final class ByteArrayTag extends ArrayTag<byte[],Byte> {
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
		return new ByteArrayTag(value);
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
		return Arrays.toString(value);
	}
}

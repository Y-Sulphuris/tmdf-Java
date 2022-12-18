package org.tmdf.tag;

import org.tmdf.TmdfUtils;

import java.util.Arrays;

import static org.tmdf.ByteBuffersCache.bb2;

public final class CharArrayTag extends ArrayTag<char[],Character> {

	private char[] value;

	public CharArrayTag(char... value) {
		this.value = value;
	}
	public CharArrayTag(int index) {
		this.value = new char[index];
	}
	public CharArrayTag(String value) {
		this.value = value.toCharArray();
	}

	@Override
	public Character get(int index) {
		return value[index];
	}

	@Override
	public void set(int index, Character x) {
		value[index] = x;
	}

	@Override
	public int length() {
		return value.length;
	}

	@Override
	public char[] getValue() {
		return value;
	}

	@Override
	public void setValue(char[] value) {
		this.value = value;
	}

	@Override
	public CharArrayTag clone() {
		return new CharArrayTag(value);
	}

	@Override
	protected byte[] getPayload() {
		byte[] bytes = new byte[payloadSize()];
		{
			byte[] intArrayLength = TmdfUtils.intToByteArray(value.length);
			System.arraycopy(intArrayLength, 0, bytes, 0, 4);
		}
		for (int i = 0; i < value.length; i++) {
			bb2.putChar(0,value[i]);
			bytes[4+i*2] = bb2.get(0);
			bytes[4+i*2+1] = bb2.get(1);
		}
		return bytes;
	}


	@Override
	public String toString() {
		return Arrays.toString(value);
	}

	@Override
	public int payloadSize() {
		return 4+length()*2;
	}
}

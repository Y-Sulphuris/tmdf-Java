package org.tmdf;

import java.util.Arrays;

import static org.tmdf.ByteBuffersCache.bb2;

/**
 * CharArrayTag: 20<br>
 * Payload: an ordered array of two-byte characters in UTF-16 format. The first 4 bytes mean the length of the array (size in bytes equals array size * 2 + 4)
 */
public final class CharArrayTag extends ArrayTag<char[],Character> {
	public boolean isShort() {
		return getFlag();
	}
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
		return new CharArrayTag(value.clone());
	}

	@Override
	protected byte[] getPayload() {
		byte[] bytes = new byte[payloadSize()];
		{
			byte[] intArrayLength = TmdfUtils.intToByteArray(value.length);
			if (isShort()) {
				intArrayLength = new byte[]{bytes[2],bytes[3]};
			}
			System.arraycopy(intArrayLength, 0, bytes, 0, intArrayLength.length);
		}
		for (int i = 0; i < value.length; i++) {
			bb2.putChar(0,value[i]);
			int offset = (isShort() ? 2 : 4) + i * 2;
			bytes[offset] = bb2.get(0);
			bytes[offset + 1] = bb2.get(1);
		}
		return bytes;
	}


	@Override
	public String toString() {
		return Arrays.toString(value);
	}

	@Override
	public int payloadSize() {
		return (isShort() ? 2 : 4) + length()*2;
	}
}

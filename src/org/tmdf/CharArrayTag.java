package org.tmdf;

import java.util.Arrays;

import static org.tmdf.ByteBuffersCache.bb2;

/**
 * 	CharArrayTag: 20<p>
 * 	Payload: an ordered array of two-byte characters in UTF-16 format. The first 4 bytes mean the length of the array (size in bytes equals array size * 2 + 4)<br>
 * 	Flag: If true, the size of the array is 2 bytes (no more than 65535). Else 4 bytes (does not exceed 2147483647)<br>
 * 	 * Use CharArrayTag if you need to put a null value there. In all other cases it is recommended to use strings (StringUTF8Tag or StringUTF16Tag) *
 */
public final class CharArrayTag extends ArrayTag<char[],Character> {

	public static CharArrayTag of(char... values) {
		return new CharArrayTag(values);
	}

	public boolean isShort() {
		return getFlag();
	}
	private char[] value;

	public CharArrayTag(char[] value) {
		this.value = value;
	}
	public CharArrayTag(int length) {
		this.value = new char[length];
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
		if (isShort() && value.length > Short.MAX_VALUE*2-1)
			throw new IndexOutOfBoundsException("array overflow");
		this.value = value;
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
	public String toContentString() {
		return Arrays.toString(value);
	}

	@Override
	public int payloadSize() {
		return (isShort() ? 2 : 4) + length()*2;
	}
}

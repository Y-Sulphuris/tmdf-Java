package org.tmdf;

import java.nio.ByteBuffer;

/**
 * 	StringUTF16Tag: 19<br>
 * 	Payload: null-terminated array of two-byte characters in UTF-16 format. Always ends with 2-bytes '\0000' (size in bytes equals string length * 2 + 2)<br>
 * 	Flag: none
 */
public final class StringUTF16Tag extends StringTag {
	{
		if (getValue().length()>255) throw new IndexOutOfBoundsException(getValue().length()+"");
	}

	public StringUTF16Tag(String value) {
		super(value);
	}


	@Override
	protected byte[] getPayload() {
		ByteBuffer bb = ByteBuffer.allocate(payloadSize());
		String value = getValue()+'\0';
		char[] chars = value.toCharArray();
		for (int i = 0; i < getValue().length(); i++) {
			bb.putChar(i*2,chars[i]);
		}
		return bb.array();
	}

	@Override
	public int payloadSize() {
		return length()*2+2;
	}

	public CharArrayTag toCharArrayTag() {
		return new CharArrayTag(getValue().toCharArray());
	}


	@Override
	public void setValue(String value) {
		if (value.length()>255) throw new IndexOutOfBoundsException(String.valueOf(getValue().length()));
		super.setValue(value);
	}
}
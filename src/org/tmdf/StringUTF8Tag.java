package org.tmdf;

import java.nio.charset.StandardCharsets;

/**
 * Type: 8<br>
 * Payload: null-terminated array of single-byte characters in UTF-8 format. Always ends with '\0000' (size in bytes equals string length + 1)
 */
public final class StringUTF8Tag extends StringTag {
	{
		if (getValue().length()>255) throw new IndexOutOfBoundsException(getValue().length()+"");
	}

	public StringUTF8Tag(String value) {
		super(TmdfUtils.checkUTF8(value));
	}

	@Override
	public StringUTF8Tag clone() {
		return new StringUTF8Tag(getValue());
	}

	@Override
	protected byte[] getPayload() {
		return (getValue() + '\0').getBytes(StandardCharsets.UTF_8);
	}

	@Override
	public int payloadSize() {
		return length()+1;
	}

	public ByteArrayTag toByteArrayTag() {
		return new ByteArrayTag((getValue() + '\0').getBytes(StandardCharsets.UTF_8));
	}
}

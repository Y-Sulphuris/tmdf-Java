package org.tmdf.tag;

import org.tmdf.TmdfUtils;

import java.nio.charset.StandardCharsets;

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

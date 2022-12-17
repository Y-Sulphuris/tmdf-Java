package org.tmdf.tag;

import org.tmdf.TmdfUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

public final class StringUTF16Tag extends StringTag {
	{
		if (getValue().length()>255) throw new IndexOutOfBoundsException(getValue().length()+"");
	}

	public StringUTF16Tag(String value) {
		super(value);
	}

	@Override
	public StringUTF16Tag clone() {
		return new StringUTF16Tag(getValue());
	}

	@Override
	protected byte[] getPayload() {
		ByteBuffer bb = ByteBuffer.allocate(getValue().length()*2);
		char[] chars = getValue().toCharArray();
		for (int i = 0; i < getValue().length(); i++) {
			bb.putChar(i*2,chars[i]);
		}
		return TmdfUtils.sum(bb.array(),new byte[]{0,0});
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
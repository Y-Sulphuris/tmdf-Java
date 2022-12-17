package org.tmdf.tag;

import org.tmdf.TmdfUtils;

import java.util.Arrays;

public final class TagArray extends ArrayTag<Tag<?>[],Tag<?>> {

	private Tag<?>[] value;

	public TagArray(Tag<?>... value) {
		this.value = value;
	}
	public TagArray(int index) {
		this.value = new Tag<?>[index];
	}

	@Override
	public Tag<?> get(int index) {
		return value[index];
	}

	@Override
	public void set(int index, Tag<?> x) {
		value[index] = x;
	}

	@Override
	public int length() {
		return value.length;
	}

	@Override
	public Tag<?>[] getValue() {
		return value;
	}

	@Override
	public void setValue(Tag<?>[] value) {
		this.value = value;
	}

	@Override
	public TagArray clone() {
		return new TagArray(value);
	}

	@Override
	protected byte[] getPayload() {
		byte[] bytes = TmdfUtils.intToByteArray(value.length);
		for (Tag<?> tag : value) {
			TmdfUtils.sum(bytes, tag.toByteArray(""));
		}
		return bytes;
	}


	@Override
	public String toString() {
		return Arrays.toString(value);
	}
}

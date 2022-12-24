package org.tmdf;

import java.util.Arrays;

/**
 * 	TagArray: 18<br>
 * 	Payload: an ordered array of unnamed tags (name = ""). Tags are contained in the array as a whole (Not only payload). The first 4 bytes mean the length of the array (size not defined)<br>
 * 	Flag: If true, the size of the array is 2 bytes (no more than 65535). Else 4 bytes (does not exceed 2147483647)
 */
public final class TagArray extends ArrayTag<Tag<?>[],Tag<?>> {
	public static TagArray of(Tag<?>... tags) {
		return new TagArray(tags);
	}

	public boolean isShort() {
		return getFlag();
	}
	public TagArray setToShort(boolean flag) {
		return (TagArray) setFlag(flag);
	}
	private Tag<?>[] value;

	public TagArray(Tag<?>[] value) {
		if (value.length<Short.MAX_VALUE) setToShort(true);
		else setToShort(false);
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
		if (value.length<Short.MAX_VALUE) setToShort(true);
		else setToShort(false);
		this.value = value;
	}

	@Override
	public TagArray clone() {
		return new TagArray(value.clone()).setToShort(isShort());
	}

	@Override
	protected byte[] getPayload() {
		byte[] bytes = TmdfUtils.intToByteArray(value.length);
		if (isShort()) {
			bytes = new byte[]{bytes[2],bytes[3]};
		}
		for (Tag<?> tag : value) {
			bytes = TmdfUtils.sum(bytes, tag.toByteArray(""));
		}

		return bytes;
	}


	@Override
	public String toString() {
		return Arrays.toString(value);
	}

	@Override
	public int payloadSize() {
		int size = isShort() ? 2 : 4;
		for (Tag<?> tag : value) {
			size += tag.tagSize("");
		}
		return size;
	}
}

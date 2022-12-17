package org.tmdf.tag;

import static org.tmdf.ByteBuffersCache.bb4;

public final class IntTag extends NumTag<Integer> {
	private int value = 0;

	public IntTag(int value) {
		this.value = value;
	}
	public IntTag() {}

	@Override
	public int compareTo(NumTag<Integer> o) {
		return Integer.compare(value,o.getValue());
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public IntTag clone() {
		return new IntTag(value);
	}

	@Override
	public byte[] getPayload() {
		bb4.putInt(0,value);
		return new byte[]{bb4.get(0),bb4.get(1),bb4.get(2),bb4.get(3)};
	}

	public int compareTo(Integer anotherInteger) {
		return getValue().compareTo(anotherInteger);
	}
}

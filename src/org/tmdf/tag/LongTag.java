package org.tmdf.tag;

import static org.tmdf.ByteBuffersCache.bb8;

public final class LongTag extends NumTag<Long> {
	private long value = 0;
	public LongTag(long value) {
		this.value = value;
	}
	public LongTag() {}
	@Override
	public int compareTo(NumTag<Long> o) {
		return Long.compare(value,o.getValue());
	}

	@Override
	public Long getValue() {
		return value;
	}

	@Override
	public void setValue(Long value) {
		this.value = value;
	}

	@Override
	public Tag<Long> clone() {
		return new LongTag(value);
	}

	@Override
	protected byte[] getPayload() {
		bb8.putLong(0,value);
		return new byte[]{
			bb8.get(0),
			bb8.get(1),
			bb8.get(2),
			bb8.get(3),
			bb8.get(4),
			bb8.get(5),
			bb8.get(6),
			bb8.get(7),
		};
	}

	public int compareTo(Long anotherLong) {
		return getValue().compareTo(anotherLong);
	}
}

package org.tmdf;

import static org.tmdf.ByteBuffersCache.bb4;

/**
 * 	IntTag: 3<br>
 * 	Payload: four-byte integer value (4 bytes)<br>
 * 	Flag: true if unsigned, false if not
 */
public final class IntTag extends NumTag<Long> {
	private int value = 0;

	public IntTag(int value) {
		this.value = value;
	}
	public IntTag() {}

	@Override
	public int compareTo(NumTag<Long> o) {
		return Long.compare(getValue(),o.getValue());
	}

	@Override
	public Long getValue() {
		return isSigned() ? (long)value : Integer.toUnsignedLong(value);
	}

	@Override
	public void setValue(Long value) {
		this.value = Math.toIntExact(value);
	}

	@Override
	public IntTag clone() {
		return (IntTag) new IntTag(value).setFlag(getFlag());
	}

	@Override
	public byte[] getPayload() {
		bb4.putInt(0,value);
		return new byte[]{bb4.get(0),bb4.get(1),bb4.get(2),bb4.get(3)};
	}

	public int compareTo(Long anotherInteger) {
		return getValue().compareTo(anotherInteger);
	}
}

package org.tmdf;

import static org.tmdf.ByteBuffersCache.bb8;

/**
 * 	LongTag: 4<br>
 * 	Payload: eight-byte integer value (8 bytes)<br>
 * 	Flag: true if unsigned, false if not
 */
public final class LongTag extends NumTag<Long> {
	private long value = 0;
	public LongTag(long value) {
		this.value = value;
	}
	public LongTag() {}
	@Override
	public int compareTo(NumTag<Long> o) {
		return isSigned() ? Long.compare(value,o.getValue()) : Long.compareUnsigned(value,o.getValue());
	}

	@Override
	@Deprecated
	public Long getValue() {
		return value;
	}

	@Override
	public void setValue(Long value) {
		this.value = value;
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
		return isSigned() ? getValue().compareTo(anotherLong) : Long.compareUnsigned(value, anotherLong);
	}

	@Override
	public String toString() {
		return isSigned() ? String.valueOf(value) : Long.toUnsignedString(value);
	}


	public long divide(long divisor) {
		return isSigned()? value/divisor : Long.divideUnsigned(value, divisor);
	}

	public long remainder(long divisor) {
		return isSigned()? value%divisor : Long.remainderUnsigned(value, divisor);
	}
}

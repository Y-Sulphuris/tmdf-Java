package org.tmdf;

import java.util.Arrays;

import static org.tmdf.ByteBuffersCache.bb8;

/**
 * Type: 16<br>
 * Payload: an ordered array of eight-byte fractional IEEE 754-2008. The first 4 bytes mean the length of the array (size in bytes equals array size * 8 + 4)
 */
public final class DoubleArrayTag extends NumArrayTag<double[], Double> {
	private double[] value;

	public DoubleArrayTag(double... value) {
		this.value = value;
	}

	@Override
	public Double get(int index) {
		return value[index];
	}

	@Override
	public void set(int index, Double x) {
		value[index] = x;
	}

	@Override
	public int length() {
		return value.length;
	}

	@Override
	public double[] getValue() {
		return value;
	}

	@Override
	public void setValue(double[] value) {
		this.value = value;
	}

	@Override
	public DoubleArrayTag clone() {
		return new DoubleArrayTag(value.clone());
	}

	@Override
	protected byte[] getPayload() {
		byte[] bytes = new byte[value.length*8+4];
		{
			byte[] intArrayLength = TmdfUtils.intToByteArray(value.length);
			System.arraycopy(intArrayLength, 0, bytes, 0, 4);
		}
		for (int i = 0; i < value.length; i++) {
			bb8.putDouble(0,value[i]);
			for (int j = 0; j < 8; j++) {
				bytes[4+i*4+j] = bb8.get(j);
			}
		}
		return bytes;
	}


	@Override
	public String toString() {
		return Arrays.toString(value);
	}

	@Override
	public int payloadSize() {
		return 4*length()*8;
	}

}

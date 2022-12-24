package org.tmdf;

import java.util.Arrays;

import static org.tmdf.ByteBuffersCache.bb4;

/**
 * 	FloatArrayTag: 15<br>
 * 	Payload: an ordered array of four-byte fractional IEEE 754-2008. The first 4 bytes mean the length of the array (size in bytes = array size * 4 + 4)<br>
 * 	Flag: none
 */
public final class FloatArrayTag extends ArrayTag<float[],Float> {
	public static FloatArrayTag of(float... values) {
		return new FloatArrayTag(values);
	}

	private float[] value;

	public FloatArrayTag(float[] value) {
		this.value = value;
	}
	public FloatArrayTag(int length) {
		this.value = new float[length];
	}

	@Override
	public Float get(int index) {
		return value[index];
	}

	@Override
	public void set(int index, Float x) {
		value[index] = x;
	}

	@Override
	public int length() {
		return value.length;
	}

	@Override
	public float[] getValue() {
		return value;
	}

	@Override
	public void setValue(float[] value) {
		this.value = value;
	}

	@Override
	public FloatArrayTag clone() {
		return new FloatArrayTag(value.clone());
	}

	@Override
	protected byte[] getPayload() {
		byte[] bytes = new byte[value.length*4+4];
		{
			byte[] intArrayLength = TmdfUtils.intToByteArray(value.length);
			System.arraycopy(intArrayLength, 0, bytes, 0, 4);
		}
		for (int i = 0; i < value.length; i++) {
			bb4.putFloat(0,value[i]);
			for (int j = 0; j < 4; j++) {
				bytes[4+i*4+j] = bb4.get(j);
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
		return 4+length()*4;
	}
}

package org.tmdf;

/**
 * 	BoolArrayTag: 17<br>
 * 	Payload: an ordered array of booleans (boolean is 1 bit). The first 4 bytes mean the 1/8 of length of the array (size in bytes = array size/8 + (4 or 2))<br>
 * 	The size of the array in bits is always a multiple of 8. If it is not, zero bits are added until the size is a multiple of 8<br>
 * 	Flag: If true, the size of the array is 2 bytes (no more than 65535*8). Else 4 bytes (does not exceed 2147483647*8)
 */
public final class BoolArrayTag extends ArrayTag<boolean[],Boolean> {

	public boolean isShort() {
		return getFlag();
	}
	public BoolArrayTag setToShort(boolean flag) {
		return (BoolArrayTag) setFlag(flag);
	}

	/**
	 * Make bits from string
	 * @param binary string of 1 and 0, spaces, underscores and dots can be used as delimiters for readability
	 */
	public static BoolArrayTag of(String binary) {
		char[] chars = binary.replace("_","").replace(" ","").replace(" ","").toCharArray();
		boolean[] booleans = new boolean[chars.length];
		for (int i = 0; i < chars.length; i++) {
			booleans[i] = Integer.parseInt(String.valueOf(chars[i])) != 0;
		}
		return of(booleans);
	}

	public static BoolArrayTag of(boolean... booleans) {
		return new BoolArrayTag(booleans);
	}
	public static BoolArrayTag of(byte[] data) {
		return new BoolArrayTag(data);
	}
	private byte[] data;

	public BoolArrayTag(boolean[] value) {
		setValue(value);
	}
	public BoolArrayTag(int lengthInBytes) {
		setToShort(lengthInBytes < Short.MAX_VALUE);

		if (lengthInBytes*8L > Integer.MAX_VALUE) throw new IllegalArgumentException();
		this.data = new byte[lengthInBytes];
	}
	BoolArrayTag(byte[] data) {
		this.data = data;
	}



	@Override
	public Boolean get(int index) {
		checkIndex(index, data.length);
		int posByte = Math.toIntExact(index / 8);
		long posBit = index%8;
		byte valByte = data[posByte];
		int valInt = valByte>>(8-(posBit+1)) & 0x0001;
		return valInt != 0;
	}

	@Override
	public void set(int index, Boolean x) {
		checkIndex(index, data.length);
		if (x) {
			data[Math.toIntExact(index / 8)] |= 1 << (7 - index % 8);
		} else {
			data[Math.toIntExact(index / 8)] &= ~(1 << (7 - index % 8));
		}
	}

	@Override
	public int length() {
		return data.length * 8;
	}

	@Override
	public boolean[] getValue() {
		boolean[] b = new boolean[data.length*8];
		for (int i = 0; i < b.length; i++) {
			b[i]=this.get(i);
		}
		return b;
	}

	@Override
	public void setValue(boolean[] value) {
		setToShort(value.length < Short.MAX_VALUE); //if value.length < Short.MAX_VALUE set flag to true

		data = new byte[value.length/8 + (value.length % 8==0 ? 0 : 1)];
		for (int i = 0; i < value.length; i++) {
			set(i,value[i]);
		}
	}

	@Override
	public BoolArrayTag clone() {
		return new BoolArrayTag(data.clone());
	}

	@Override
	protected byte[] getPayload() {
		byte[] bytes = TmdfUtils.intToByteArray(data.length);
		if (isShort()) {
			bytes = new byte[]{bytes[2],bytes[3]};
		}
		System.out.println(TmdfUtils.sum(bytes,data).length + " " + payloadSize());
		return TmdfUtils.sum(bytes,data);
	}
	@Override
	public int payloadSize() {
		return (isShort() ? 2 : 4) + data.length;
	}

	@Override
	public String toContentString() {
		StringBuilder str1 = new StringBuilder();
		for (int index = 0; index < data.length; index++) {
			char[] chars1 = Integer.toBinaryString(data[index]).toCharArray();
			char[] chars2 = new char[8];
			for (int i = 1; i <= 8; i++) {
				try {
					chars2[8-i] = chars1[chars1.length-i];
				} catch (ArrayIndexOutOfBoundsException e) {
					chars2[8-i] = '0';
				}
			}
			str1.append(new String(chars2).intern());
			if (index!=data.length-1)str1.append("_");
		}
		return str1.toString();
	}
	private static void checkIndex(long index, int length) {
		length <<= 3; //length * 8
		if (index>=length)
			throw new ArrayIndexOutOfBoundsException(index + " >= " + length);
	}

}

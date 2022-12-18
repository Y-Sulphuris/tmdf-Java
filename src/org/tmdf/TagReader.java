package org.tmdf;

import java.nio.ByteBuffer;

import static org.tmdf.TmdfUtils.*;

public final class TagReader {
	private int counter = 0;
	private final ByteBuffer data;


	public ByteBuffer data() {
		return data;
	}

	public int getAndAdd(final int count) {
		int oldValue = counter;
		counter += count;
		return oldValue;
	}

	public TagReader(byte[] data) {
		this.data = ByteBuffer.wrap(data);
	}



	public Tag<?> nextUnnamedTag() {
		return nextTag().tag;
	}

	public NamedTag nextTag() {
		byte type = readByte();

		boolean flag = getTagFlag(type);
		type = setTagFlag(type,false);

		byte namelength = readByte();
		char[] chars = new char[(int)namelength];
		for (int i = 0; i < namelength; i++) {
			chars[i] = (char) readByte();
		}
		String name = new String(chars);
		Tag<?> tag = readPayload(type, flag).setFlag(flag);

		return new NamedTag(name,tag);
	}

	private Tag<?> readPayload(byte type, boolean flag) {

		switch (type) {
			case 1:
				return new ByteTag(readByte());
			case 2:
				return new ShortTag(readShort());
			case 3:
				return new IntTag(readInt());
			case 4:
				return new LongTag(readLong());
			case 5:
				return new FloatTag(readFloat());
			case 6:
				return new DoubleTag(readDouble());
			case 7:
				return BoolTag.FALSE;
			case 8:
				byte[] data = new byte[0];
				while (seeNextByte()!=0) {
					data = TmdfUtils.sum(data,new byte[]{readByte()});
				}
				readByte();
				char[] chars = new char[data.length];
				for (int i = 0; i < data.length; i++) {
					chars[i] = (char)data[i];
				}
				return new StringUTF8Tag(new String(chars));
			case 9:
				TagList list = new TagList();
				while (seeNextByte() != 0) {
					list.add(nextUnnamedTag());
				}
				readByte();
				return list;
			case 10:
				TagMap map = new TagMap();
				while (seeNextByte() != 0) {
					NamedTag namedTag = nextTag();
					map.put(namedTag.name,namedTag.tag);
				}
				readByte();
				return map;
			case 11:
				ByteArrayTag barray = new ByteArrayTag(readInt());
				for (int i = 0; i < barray.length(); i++) {
					barray.set(i, readByte());
				}
				return barray;
			case 12:
				ShortArrayTag sarray = new ShortArrayTag(readInt());
				for (int i = 0; i < sarray.length(); i++) {
					sarray.set(i,readShort());
				}
				return sarray;
			case 13:
				IntArrayTag iarray = new IntArrayTag(readInt());
				for (int i = 0; i < iarray.length(); i++) {
					iarray.set(i,readInt());
				}
				return iarray;
			case 14:
				LongArrayTag larray = new LongArrayTag(readInt());
				for (int i = 0; i < larray.length(); i++) {
					larray.set(i,readLong());
				}
				return larray;
			case 15:
				FloatArrayTag farray = new FloatArrayTag(readInt());
				for (int i = 0; i <  farray.length(); i++) {
					farray.set(i,readFloat());
				}
				return farray;
			case 16:
				DoubleArrayTag darray = new DoubleArrayTag(readInt());
				for (int i = 0; i <  darray.length(); i++) {
					darray.set(i,readDouble());
				}
				return darray;
			case 17:
				byte[] bodata = new byte[flag ? readShort() : readInt()];
				for (int i = 0; i < bodata.length; i++) {
					bodata[i] = readByte();
				}
				return new BoolArrayTag(bodata);
			case 18:
				TagArray tarray = new TagArray(flag ? readShort() : readInt());
				for (int i = 0; i <  tarray.length(); i++) {
					tarray.set(i,nextUnnamedTag());
				}
				return tarray;

			case 19:
				char[] cdata = new char[0];
				while (seeNextShort() != 0) {
					cdata = TmdfUtils.sum(cdata,new char[]{readChar()});
				}
				readShort();
				return new StringUTF16Tag(new String(cdata));

			case 20:
				CharArrayTag carray = new CharArrayTag(readInt());
				for (int i = 0; i < carray.length(); i++) {
					carray.set(i,readChar());
				}
				return carray;
			default:
				throw new UnknownTagException();
		}
	}


	public byte seeNextByte() {
		return data.get(counter);
	}
	public short seeNextShort() {
		return data.getShort(counter);
	}
	public byte readByte() {
		return data.get(counter++);
	}
	public short readShort() {
		return data.getShort(getAndAdd(2));
	}
	public char readChar() {
		return data.getChar(getAndAdd(2));
	}
	public int readInt() {
		return data.getInt(getAndAdd(4));
	}
	public float readFloat() {
		return data.getFloat(getAndAdd(4));
	}
	public long readLong() {
		return data.getLong(getAndAdd(8));
	}
	public double readDouble() {
		return data.getDouble(getAndAdd(8));
	}


	final static class NamedTag {
		public String name;

		public Tag<?> tag;
		public NamedTag(String name, Tag<?> tag) {
			this.name = name;
			this.tag = tag;
		}
	}
}

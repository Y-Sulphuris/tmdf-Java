package org.tmdf;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Arrays;

import static org.tmdf.TmdfUtils.*;

public final class TagReader {

	public static NamedTag read(File file) {
		try {
			return TagReader.read(new DataInputStream(Files.newInputStream(file.toPath())));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static NamedTag read(DataInputStream is) throws IOException {
		byte type = is.readByte();
		return read0(is,type);
	}
	private static NamedTag read0(DataInputStream is, byte first_byte_type_) throws IOException {

		boolean flag = getTagFlag(first_byte_type_);
		byte type = setTagFlag(first_byte_type_,false);

		int namelength = Byte.toUnsignedInt(is.readByte());
		char[] chars = new char[namelength];
		for (int i = 0; i < namelength; i++) {
			chars[i] = (char) is.readByte();
		}
		String name = new String(chars);
		Tag<?> tag = readPayload(is, type, flag).setFlag(flag);

		return new NamedTag(name,tag);
	}
	private static Tag<?> readPayload(DataInputStream is, byte type, boolean flag) throws IOException {
		switch (type) {
			case 1:
				return new ByteTag(is.readByte());
			case 2:
				return new ShortTag(is.readShort());
			case 3:
				return new IntTag(is.readInt());
			case 4:
				return new LongTag(is.readLong());
			case 5:
				return new FloatTag(is.readFloat());
			case 6:
				return new DoubleTag(is.readDouble());
			case 7:
				return BoolTag.FALSE;
			case 8:
				byte[] data = new byte[0];

				byte next = is.readByte();
				while (next!=0) {
					data = TmdfUtils.sum(data,new byte[]{next});
					next = is.readByte();
				}

				char[] chars = new char[data.length];
				for (int i = 0; i < data.length; i++) {
					chars[i] = (char)data[i];
				}

				return new StringUTF8Tag(new String(chars));
			case 9:
				TagList list = new TagList();
				byte next_ = is.readByte();
				while (next_ != 0) {
					list.add(read0(is,next_).tag);
					next_ = is.readByte();
				}
				return list;
			case 10:
				TagMap map = new TagMap();
				byte next__ = is.readByte();
				while (next__ != 0) {
					NamedTag namedTag = read0(is,next__);
					map.put(namedTag.name,namedTag.tag);
					next__ = is.readByte();
				}

				return map;
			case 11:
				ByteArrayTag barray = new ByteArrayTag(is.readInt());
				for (int i = 0; i < barray.length(); i++) {
					barray.set(i, is.readByte());
				}
				return barray;
			case 12:
				ShortArrayTag sarray = new ShortArrayTag(is.readInt());
				for (int i = 0; i < sarray.length(); i++) {
					sarray.set(i,is.readShort());
				}
				return sarray;
			case 13:
				IntArrayTag iarray = new IntArrayTag(is.readInt());
				for (int i = 0; i < iarray.length(); i++) {
					iarray.set(i,is.readInt());
				}
				return iarray;
			case 14:
				LongArrayTag larray = new LongArrayTag(is.readInt());
				for (int i = 0; i < larray.length(); i++) {
					larray.set(i,is.readLong());
				}
				return larray;
			case 15:
				FloatArrayTag farray = new FloatArrayTag(is.readInt());
				for (int i = 0; i <  farray.length(); i++) {
					farray.set(i,is.readFloat());
				}
				return farray;
			case 16:
				DoubleArrayTag darray = new DoubleArrayTag(is.readInt());
				for (int i = 0; i <  darray.length(); i++) {
					darray.set(i,is.readDouble());
				}
				return darray;
			case 17:
				byte[] bodata = new byte[flag ? Short.toUnsignedInt(is.readShort()) : is.readInt()];
				for (int i = 0; i < bodata.length; i++) {
					bodata[i] = is.readByte();
				}
				return new BoolArrayTag(bodata);
			case 18:
				TagArray tarray = new TagArray(flag ? Short.toUnsignedInt(is.readShort()) : is.readInt());
				for (int i = 0; i <  tarray.length(); i++) {
					tarray.set(i,read(is).tag);
				}
				return tarray;

			case 19:
				char[] cdata = new char[0];
				char next___ = is.readChar();
				while (next___ != 0) {
					cdata = TmdfUtils.sum(cdata,new char[]{next___});
					next___ = is.readChar();
				}
				return new StringUTF16Tag(new String(cdata));

			case 20:
				CharArrayTag carray = new CharArrayTag(flag ? Short.toUnsignedInt(is.readShort()) : is.readInt());
				for (int i = 0; i < carray.length(); i++) {
					carray.set(i,is.readChar());
				}
				return carray;
			default:
				throw new UnknownTagException();
		}
	}



	public int counter() {
		return counter;
	}
	private int counter = 0;
	public void reset() {
		counter = 0;
	}
	private final ByteBuffer data_b;

	public byte[] data() {
		return data_b.array();
	}
	public ByteBuffer buffer() {
		return data_b;
	}

	public int getAndAdd(final int count) {
		int oldValue = counter;
		counter += count;
		return oldValue;
	}
	public void resetCounter() {
		counter = 0;
	}

	public TagReader(final byte[] data, final boolean compressed) {
		this.data_b = ByteBuffer.wrap(compressed ? TmdfUtils.uncompress(data) : data);
	}

	public TagReader(final byte[] data) {
		this(data, false);
	}


	public Tag<?> nextUnnamedTag() {
		return nextTag().tag;
	}

	public NamedTag nextTag() {
		byte type = readByte();

		boolean flag = getTagFlag(type);
		type = setTagFlag(type,false);

		int namelength = Byte.toUnsignedInt(readByte());
		char[] chars = new char[namelength];
		for (int i = 0; i < namelength; i++) {
			chars[i] = (char) readByte();
		}
		String name = new String(chars);
		Tag<?> tag = readPayload(type, flag).setFlag(flag);

		return new NamedTag(name,tag);
	}

	private Tag<?> readPayload(final byte type, final boolean flag) {

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
				counter++; //skip 0 at the end
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
				counter++;
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
				byte[] bodata = new byte[flag ? Short.toUnsignedInt(readShort()) : readInt()];
				for (int i = 0; i < bodata.length; i++) {
					bodata[i] = readByte();
				}
				return new BoolArrayTag(bodata);
			case 18:
				TagArray tarray = new TagArray(flag ? Short.toUnsignedInt(readShort()) : readInt());
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
				CharArrayTag carray = new CharArrayTag(flag ? Short.toUnsignedInt(readShort()) : readInt());
				for (int i = 0; i < carray.length(); i++) {
					carray.set(i,readChar());
				}
				return carray;
			default:
				throw new UnknownTagException();
		}
	}


	public byte seeNextByte() {
		return data_b.get(counter);
	}
	public short seeNextShort() {
		return data_b.getShort(counter);
	}
	public byte readByte() {
		return data_b.get(counter++);
	}
	public short readShort() {
		return data_b.getShort(getAndAdd(2));
	}
	public char readChar() {
		return data_b.getChar(getAndAdd(2));
	}
	public int readInt() {
		return data_b.getInt(getAndAdd(4));
	}
	public float readFloat() {
		return data_b.getFloat(getAndAdd(4));
	}
	public long readLong() {
		return data_b.getLong(getAndAdd(8));
	}
	public double readDouble() {
		return data_b.getDouble(getAndAdd(8));
	}



	//todo: возвращает указатель на полезную нагрузку тега по имени
	/**
	 * this.data_b = ByteBuffer.wrap(data);
	 */
	@Deprecated
	public int tagPayloadOffset(final int typeID,final String name) {
		int globalCounter = counter;
		counter = 0;

		String[] names = name.split("/");


		for (int i = 0; i < names.length; i++) {
			byte id;
			try {
				id = readByte();
			} catch (IndexOutOfBoundsException e) {
				return goToGlobalAndReturnsLocal(globalCounter);
			}
			boolean flag = TmdfUtils.getTagFlag(id); //save flag
			id = TmdfUtils.setTagFlag(id,false); //and turn it off

//			Class<?> type = Tag.getType(id);
//			System.out.println(type.getName());

			//read name
			byte nameLength = readByte();
			final byte[] thisName = new byte[nameLength+1];

			thisName[0] = nameLength;//полный массив имени включая длинну
			for (int ii = 1; ii < nameLength+1; ii++) {
				thisName[ii] = readByte();
			}

			//after reading name
			if (Arrays.equals(thisName, TmdfUtils.stringToTMDFNameByteArray(names[i]))) { //если имя совпадает с текущим выбраным фрагментом из полного пути
				if (typeID == id && i == (names.length - 1) ) {
					return goToGlobalAndReturnsLocal(globalCounter);
				} else if (typeID == 10) {//это ещё один tagmap
					continue;
				}
			} else {
				skipPayload(id,flag);
				--i;
				continue;
			}
		}


		goToGlobalAndReturnsLocal(globalCounter);
		return -1;
	}
	@Deprecated
	public Tag<?> getTag(final int typeID,final String name) {
		NamedTag namedTag = getNamedTag(typeID, name);
		if (namedTag == null)
			return null;
		return namedTag.getTag();
	}
	@Deprecated
	public NamedTag getNamedTag(int typeID, String name) {
		int globalCounter = counter;
		counter = 0;

		String[] names = name.split("/");


		for (int i = 0; i < names.length; i++) {
			byte id = readByte();
			boolean flag = TmdfUtils.getTagFlag(id); //save flag
			id = TmdfUtils.setTagFlag(id,false); //and turn it off

			Class<?> type = Tag.getType(id);
			System.out.println(type.getName());

			//read name
			byte nameLength = readByte();
			final byte[] thisName = new byte[nameLength+1];

			thisName[0] = nameLength;//полный массив имени включая длинну
			for (int ii = 1; ii < nameLength+1; ii++) {
				thisName[ii] = readByte();
			}

			//after reading name
			if (Arrays.equals(thisName, TmdfUtils.stringToTMDFNameByteArray(names[i]))) { //если имя совпадает с текущим выбраным фрагментом из полного пути
				if (typeID == id && i == (names.length - 1) ) {
					counter = counter - nameLength - 1;
					NamedTag result = nextTag();
					goToGlobalAndReturnsLocal(globalCounter);
					return result;
				} else if (typeID == 10) {//это ещё один tagmap
					continue;
				}
			} else {
				skipPayload(id,flag);
				--i;
				continue;
			}
		}


		goToGlobalAndReturnsLocal(globalCounter);
		return null;
	}
	private int goToGlobalAndReturnsLocal(int globalCounter) {

		//возвращает локальный счётчик (если сейчас активен)
		//и присваивает счётчику снова глобальное значение
		int result = this.counter;
		this.counter = globalCounter;
		return result;
	}

	/**
	 * <blockquote><pre>
	 * TagMap("map") = {
	 * 	ByteTag("A") = 7
	 * 	StringUTF8Tag("B") = "hello world"
	 * }</pre></blockquote>
	 */
	private static final byte[] testMap = {10, 3, 109, 97, 112, -127, 1, 65, 7, 8, 1, 66, 104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 0, 0};
	private static void test() {
		System.out.println(new TagReader(testMap).tagPayloadOffset((byte) 1,"map/A"));
	}




	//всё идиальна
	private void skipTag() {
		byte id = readByte();
		boolean flag = TmdfUtils.getTagFlag(id); //save flag
		id = TmdfUtils.setTagFlag(id,false); //and turn it off

		byte nameLength = readByte();
		for (int ii = 0; ii < nameLength; ii++) {
			counter++;
		}
		skipPayload(id,flag);
	}

	//когда type и имя уже прочитано (имя не имеет значения)
	private void skipPayload(byte type, boolean flag) {
		switch (type) {
			case 1://byte and boolean
			case 7:
				counter += 1;
				return;
			case 2://short
				counter += 2;
				return;
			case 3://int and float
			case 5:
				counter += 4;
				return;
			case 4://long and double
			case 6:
				counter += 8;
				return;
			case 8:
				while (seeNextByte()!=0) {
					counter++;
				}
				counter++;
				return;
			case 9:
			case 10: //tagmap
				while (seeNextByte() != 0) {
					skipTag();
				}
				counter++;
				return;
			case 11: { //byte array
				int length = readInt();
				for (int i = 0; i < length; i++)
					counter++;
			}	return;
			case 12://char array (no char array)
			{
				int length = readInt();
				for (int i = 0; i < length; i++)
					counter += 2;

			}	return;
			case 13://int array or float array
			case 15: {
				int length = readInt();
				for (int i = 0; i < length; i++)
					counter += 4;

			}	return;
			case 14: //long array or double array
			case 16: {
				int length = readInt();
				for (int i = 0; i < length; i++)
					counter += 8;

			}	return;
			case 17:
				int lengthAsBytes = flag ? Short.toUnsignedInt(readShort()) : readInt();
				for (int i = 0; i < lengthAsBytes; i++) {
					counter++;
				}
				return;
			case 18:{
				int length = flag ? Short.toUnsignedInt(readShort()) : readInt();
				for (int i = 0; i <  length; i++) {
					skipTag();
				}
			}	return;

			case 19:
				while (seeNextShort() != 0) {
					counter += 2;
				}
				counter += 2;
				return;
			case 20: {
				int length = flag ? Short.toUnsignedInt(readShort()) : readInt();
				for (int i = 0; i < length; i++)
					counter += 2;

			}	return;
			default:
				throw new UnknownTagException(String.valueOf(type));
		}
	}
}

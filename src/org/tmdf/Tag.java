package org.tmdf;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.tmdf.TmdfUtils.*;

public abstract class Tag<T> implements Cloneable{

	private boolean flag = false;

	protected Tag<T> setFlag(boolean flag) {
		this.flag = flag;
		return this;
	}
	protected boolean getFlag() {
		return flag;
	}

	/**
	 * Packs an object into a tag based on its type<br>
	 * Collections are not supported
	 */
	public static Tag<?> wrap(Object x) {
		if (x == null) return null;
		if (x instanceof Byte) return new ByteTag((Byte) x);
		if (x instanceof Short) return new ShortTag((Short) x);
		if (x instanceof Integer) return new IntTag((Integer) x);
		if (x instanceof Long) return new LongTag((Long) x);
		if (x instanceof Float) return new FloatTag((Float) x);
		if (x instanceof Double) return new DoubleTag((Double) x);
		if (x instanceof Boolean) return BoolTag.of((Boolean) x);
		//if (x instanceof ArrayList) return new TagList((ArrayList<Tag<?>>) x);
		//if (x instanceof HashMap) return new TagMap((HashMap<String, Tag<?>>) x);
		if (x instanceof byte[]) return new ByteArrayTag((byte[]) x);
		if (x instanceof short[]) return new ShortArrayTag((short[]) x);
		if (x instanceof int[]) return new IntArrayTag((int[]) x);
		if (x instanceof long[]) return new LongArrayTag((long[]) x);
		if (x instanceof float[]) return new FloatArrayTag((float[]) x);
		if (x instanceof double[]) return new DoubleArrayTag((double[]) x);
		if (x instanceof boolean[]) return new BoolArrayTag((boolean[]) x);
		if (x instanceof char[]) return new CharArrayTag((char[]) x);
		if (x instanceof Tag<?>[]) return new TagArray((Tag<?>[]) x);
		if (x instanceof String) {
			String s = (String) x;
			if (s.contains("\0")) {
				return new CharArrayTag(s);
			} else {
				if (s.getBytes(StandardCharsets.UTF_8).length == s.length()) {
					return new StringUTF8Tag(s);
				} else return new StringUTF16Tag(s);
			}
		}
		return new Tag<Void>() {
			@Override
			public Void getValue() {
				throw new UnknownTagException("Tag<void>");
			}

			@Override
			public void setValue(Void value) {
				throw new UnknownTagException("Tag<void>");
			}

			@Override
			public Tag<Void> clone() {
				throw new UnknownTagException("Tag<void>");
			}

			@Override
			protected byte[] getPayload() {
				throw new UnknownTagException("Tag<void>");
			}

			@Override
			public String toString() {
				return "Tag<void>";
			}

			@Override
			public int payloadSize() {
				return 0;
			}
		};
	}


	//internal constructor
	Tag() {}

	/**
	 * There are several types of tags:
	 <br>* All numbers use big endian *
	 <br>* If Flag is marked as none, it is always 0 *
	 <br>* Zero means the end of the collection or string if it is where the next tag ID is expected *
<br>
	 <br>ByteTag: 1
	 <br>Payload: single-byte integer value (1 byte)
	 <br>Flag: true if unsigned, false if not
<br>
	 <br>ShortTag: 2
	 <br>Payload: two-byte integer value (2 bytes)
	 <br>Flag: true if unsigned, false if not
<br>
	 <br>IntTag: 3
	 <br>Payload: four-byte integer value (4 bytes)
	 <br>Flag: true if unsigned, false if not
<br>
	 <br>LongTag: 4
	 <br>Payload: eight-byte integer value (8 bytes)
	 <br>Flag: true if unsigned, false if not
<br>
	 <br>FloatTag: 5
	 <br>Payload: four-byte fractional value (4 bytes) - IEEE 754-2008
	 <br>Flag: none
<br>
	 <br>DoubleTag: 6
	 <br>Payload: eight-byte fractional value (8 bytes) - IEEE 754-2008
	 <br>Flag: none
<br>
	 <br>BoolTag: 7
	 <br>Payload: none
	 <br>Flag: boolean value of tag (1 if true, 0 if false)
<br>
	 <br>StringUTF8Tag: 8
	 <br>Payload: null-terminated array of single-byte characters in UTF-8 format. Always ends with '\00' (size in bytes equals string length + 1)
	 <br>Flag: none
<br>
	 <br>TagList: 9
	 <br>Payload: unordered array of tags without a name (name = ""). Always null-terminated (size not defined)
	 <br>Flag: none
<br>
	 <br>TagMap: 10
	 <br>Payload: unordered array of named tags. Always null-terminated (size not defined)
	 <br>Flag: none
<br>
	 <br>ByteArrayTag: 11
	 <br>Payload: an ordered array of one-byte integers. The first 4 bytes mean the length of the array (size in bytes = array size + 4)
	 <br>Flag: If true, all values are unsigned, else all values are signed
<br>
	 <br>ShortArrayTag: 12
	 <br>Payload: an ordered array of two-byte integers. The first 4 bytes mean the length of the array (size in bytes = array size * 2 + 4)
	 <br>Flag: If true, all values are unsigned, else all values are signed
<br>
	 <br>IntArrayTag: 13
	 <br>Payload: an ordered array of four-byte integers. The first 4 bytes mean the length of the array (size in bytes = array size * 4 + 4)
	 <br>Flag: If true, all values are unsigned, else all values are signed
<br>
	 <br>LongArrayTag: 14
	 <br>Payload: an ordered array of eight-byte integers. The first 4 bytes mean the length of the array (size in bytes = array size * 8 + 4)
	 <br>Flag: If true, all values are unsigned, else all values are signed
<br>
	 <br>FloatArrayTag: 15
	 <br>Payload: an ordered array of four-byte fractional IEEE 754-2008. The first 4 bytes mean the length of the array (size in bytes = array size * 4 + 4)
	 <br>Flag: none
<br>
	 <br>DoubleArrayTag: 16
	 <br>Payload: an ordered array of eight-byte fractional IEEE 754-2008. The first 4 bytes mean the length of the array (size in bytes = array size * 8 + 4)
	 <br>Flag: none
<br>
	 <br>BoolArrayTag: 17
	 <br>Payload: an ordered array of booleans (boolean is 1 bit). The first 4 bytes mean the 1/8 of length of the array (size in bytes = array size/8 + (4 or 2))
	 <br>The size of the array in bits is always a multiple of 8. If it is not, zero bits are added until the size is a multiple of 8
	 <br>Flag: If true, the size of the array is 2 bytes (no more than 65535*8). Else 4 bytes (does not exceed 2147483647*8)
<br>
	 <br>TagArray: 18
	 <br>Payload: an ordered array of unnamed tags (name = ""). Tags are contained in the array as a whole (Not only payload). The first 4 bytes mean the length of the array (size not defined)
	 <br>Flag: If true, the size of the array is 2 bytes (no more than 65535). Else 4 bytes (does not exceed 2147483647)
<br>
	 <br>StringUTF16Tag: 19
	 <br>Payload: null-terminated array of two-byte characters in UTF-16 format. Always ends with 2-bytes '\0000' (size in bytes equals string length * 2 + 2)
	 <br>Flag: none
<br>
	 <br>CharArrayTag: 20
	 <br>Payload: an ordered array of two-byte characters in UTF-16 format. The first 4 bytes mean the length of the array (size in bytes equals array size * 2 + 4)
	 <br>Flag: If true, the size of the array is 2 bytes (no more than 65535). Else 4 bytes (does not exceed 2147483647)
	 <br>* Use CharArrayTag if you need to put a null value there. In all other cases it is recommended to use strings (StringUTF8Tag or StringUTF16Tag) *
<br>
<br>
	 <br>It is recommended to use TagMap as the root of the hierarchy in the file, but this requirement is not mandatory: any tag can be located at the root

	 */
	private static final Class<?>[] types = {
		null,
		ByteTag.class,
		ShortTag.class,
		IntTag.class,
		LongTag.class,
		FloatTag.class,
		DoubleTag.class,
		BoolTag.class,
		StringUTF8Tag.class,
		TagList.class,
		TagMap.class,
		ByteArrayTag.class,
		ShortArrayTag.class,
		IntArrayTag.class,
		LongArrayTag.class,
		FloatArrayTag.class,
		DoubleArrayTag.class,
		BoolArrayTag.class,
		TagArray.class,
		StringUTF16Tag.class,
		CharArrayTag.class
	};

	public abstract T getValue();
	public abstract void setValue(T value);

	@Override
	public abstract Tag<T> clone();

	/**
	 * Integer type of tag
	 * @see Tag#types
	 */
	public final byte getID() {
		for (byte i = 0; i < types.length; i++) {
			if (types[i] == this.getClass()) return i;
		}
		throw new RuntimeException(this.getClass().getCanonicalName());
	}
	public static Class<?> getType(byte id) {
		return types[id];
	}

	/**
	 * The tag consists of several components:<br>
	 * ------------------------------------<br>
	 *<br>
	 * Special flag - [1 bit] (see {@link Tag#flag})<br>
	 * TagType - [7 bit]<br>
	 * TagNameLength - [1 byte] (unsigned one-byte integer value)<br>
	 * TagName - [TagNameLength bytes] (UTF-8)<br>
	 * PayLoad - (size depends on tag type) — {@link Tag#getPayload()}<br>
	 *<br>
	 * ------------------------------------<br>
	 * @param name name of this tag
	 * @return byteArray of this tag (convert to binary)
	 */
	public final byte[] toByteArray(String name) {
		name = TmdfUtils.checkUTF8(name);

		byte[] bytes = new byte[tagSize(name)];
		bytes[0] = setTagFlag(getID(),getFlag());

		int offset = 1;

		//get name of tag
		byte[] nameBytes = stringToTMDFNameByteArray(name);
		System.arraycopy(nameBytes, 0, bytes, offset, nameBytes.length);

		offset += nameBytes.length;

		//get payload (implementation in tag)
		byte[] payload = getPayload();
		System.arraycopy(payload, 0, bytes, offset, payload.length);
		return bytes;//sum(new byte[]{getID()},nameBytes,payload);
	}

	@Deprecated
	public final byte[] toCompressedByteArray(String name) {
		return null;//compress(toByteArray(name));
	}
	/**
	 * Get only payload of this tag, without type and name data<br>
	 * (for example: IntTag(4) -> [0,0,0,4])
	 */
	protected abstract byte[] getPayload();

	public final File dump(String name, String path, @Deprecated boolean compress) {
		File file = new File(path);
		try {
			Files.write(file.toPath(),compress ? toCompressedByteArray(name) : toByteArray(name));
			return file;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Tag<?> tag = (ByteTag) o;
		return getValue() == tag.getValue();
	}

	@Override
	public final int hashCode() {
		return Objects.hash(super.hashCode(), getValue());
	}

	@Override
	public String toString() {
		return getValue().toString();
	}

	/**
	 * get size in binary
	 * @param name name of tag (null -> "")
	 */
	public final int tagSize(String name) {
		return 1 + TmdfUtils.TMDFNameByteLength(name) + payloadSize();
	}
	public abstract int payloadSize();

	public String toGenericString(String name) {
		return getClass().getSimpleName() + "(\"" + name + "\")" + (getFlag()?'*':"") + " = " + this;
	}


	public final NamedTag setName(String name) {
		return new NamedTag(name,this);
	}


}

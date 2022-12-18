package org.tmdf.tag;

import org.tmdf.TmdfUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.tmdf.TmdfUtils.*;

public abstract class Tag<T> implements Cloneable{

	public static Tag<?> wrap(Object x) {
		if (x == null) return null;
		if (x instanceof Byte) return new ByteTag((Byte) x);
		if (x instanceof Short) return new ShortTag((Short) x);
		if (x instanceof Integer) return new IntTag((Integer) x);
		if (x instanceof Long) return new LongTag((Long) x);
		if (x instanceof Float) return new FloatTag((Float) x);
		if (x instanceof Double) return new DoubleTag((Double) x);
		if (x instanceof Boolean) return BooleanTag.of((Boolean) x);
		//if (x instanceof List) return new TagList((List<Tag<?>>) x);
		//if (x instanceof Map) return new TagMap((Map<String, Tag<?>>) x);
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




	Tag() {}

	private static final Class<?>[] types = {
		null,
		ByteTag.class,
		ShortTag.class,
		IntTag.class,
		LongTag.class,
		FloatTag.class,
		DoubleTag.class,
		BooleanTag.class,
		StringUTF8Tag.class,
		TagList.class,
		TagMap.class,
		ByteArrayTag.class,
		ShortArrayTag.class,
		IntArrayTag.class,
		LongArrayTag.class,
		FloatArrayTag.class,
		DoubleArrayTag.class,
		BooleanArrayTag.class,
		TagArray.class,
		StringUTF16Tag.class,
		CharArrayTag.class
	};

	public abstract T getValue();
	public abstract void setValue(T value);

	@Override
	public abstract Tag<T> clone();

	public final byte getID() {

		for (byte i = 0; i < types.length; i++) {
			if (types[i] == this.getClass()) return i;
		}
		throw new RuntimeException(this.getClass().getCanonicalName());
	}
	public final byte[] toByteArray(String name) {
		name = TmdfUtils.checkUTF8(name);

		byte[] bytes = new byte[tagSize(name)];
		bytes[0] = getID();

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
	protected abstract byte[] getPayload();



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

	public final int tagSize(String name) {
		return 1 + TmdfUtils.TMDFNameByteLength(name) + payloadSize();
	}
	public abstract int payloadSize();
}

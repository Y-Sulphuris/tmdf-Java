package org.tmdf;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 	TagMap: 10<br>
 * 	Payload: unordered array of named tags. Always null-terminated (size not defined)<br>
 * 	Flag: none
 */
public final class TagMap extends CollectionTag<Map<String, Tag<?>>> implements Map<String,Tag<?>>{
	private Map<String,Tag<?>> map;

	public TagMap(Map<String, Tag<?>> map) {
		this.map = map;
	}
	public TagMap() {
		this.map = new HashMap<>();
	}


	public TagMap add(NamedTag namedTag) {
		return put(namedTag.name,namedTag.tag);
	}
	public TagMap put(String name, Tag<?> tag) {
		if (name == null) return this;
		map.put(name,tag);
		return this;
	}
	public TagMap putByte(String name, byte v) {
		return put(name,new ByteTag(v));
	}
	public TagMap putShort(String name, short v) {
		return put(name,new ShortTag(v));
	}
	public TagMap putInt(String name, int v) {
		return put(name,new IntTag(v));
	}
	public TagMap putLong(String name, long v) {
		return put(name,new LongTag(v));
	}
	public TagMap putFloat(String name, float v) {
		return put(name,new FloatTag(v));
	}
	public TagMap putDouble(String name, double v) {
		return put(name,new DoubleTag(v));
	}
	public TagMap putBoolean(String name, boolean v) {
		return put(name,BoolTag.of(v));
	}
	public TagMap putString(String name, String v) {
		return put(name,Tag.wrap(v));
	}
	public TagMap putList(String name, List<Tag<?>> v) {
		return put(name,new TagList(v));
	}
	public TagMap putMap(String name, Map<String,Tag<?>> v) {
		return put(name,new TagMap(v));
	}
	public TagMap putByteArray(String name, byte[] v) {
		return put(name,new ByteArrayTag(v));
	}
	public TagMap putShortArray(String name, short[] v) {
		return put(name,new ShortArrayTag(v));
	}
	public TagMap putIntArray(String name, int[] v) {
		return put(name,new IntArrayTag(v));
	}
	public TagMap putLongArray(String name, long[] v) {
		return put(name,new LongArrayTag(v));
	}
	public TagMap putFloatArray(String name, float[] v) {
		return put(name,new FloatArrayTag(v));
	}
	public TagMap putDoubleArray(String name, double[] v) {
		return put(name,new DoubleArrayTag(v));
	}
	public TagMap putBooleanArray(String name, boolean[] v) {
		return put(name,new BoolArrayTag(v));
	}
	public TagMap putTagArray(String name, Tag<?>[] v) {
		return put(name,new TagArray(v));
	}
	public TagMap putCharArray(String name, char[] v) {
		return put(name,new CharArrayTag(v));
	}


	public ByteTag getByte(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof ByteTag) return ((ByteTag) tag);
		return null;
	}
	public ShortTag getShort(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof ShortTag) return ((ShortTag) tag);
		return null;
	}
	public IntTag getInt(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof IntTag) return ((IntTag) tag);
		return null;
	}
	public LongTag getLong(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof LongTag) return ((LongTag) tag);
		return null;
	}
	public FloatTag getFloat(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof FloatTag) return ((FloatTag) tag);
		return null;
	}
	public DoubleTag getDouble(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof DoubleTag) return ((DoubleTag) tag);
		return null;
	}
	public BoolTag getBoolean(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof BoolTag) return ((BoolTag) tag);
		return null;
	}
	public StringTag getString(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof StringTag) return ((StringTag) tag);
		return null;
	}
	public TagList getList(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof TagList) return ((TagList) tag);
		return null;
	}
	public TagMap getMap(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof TagMap) return ((TagMap) tag);
		return null;
	}
	public ByteArrayTag getByteArray(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof ByteArrayTag) return ((ByteArrayTag) tag);
		return null;
	}
	public ShortArrayTag getShortArray(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof ShortArrayTag) return ((ShortArrayTag) tag);
		return null;
	}
	public IntArrayTag getIntArray(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof IntArrayTag) return ((IntArrayTag) tag);
		return null;
	}
	public LongArrayTag getLongArray(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof LongArrayTag) return ((LongArrayTag) tag);
		return null;
	}
	public FloatArrayTag getFloatArray(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof FloatArrayTag) return ((FloatArrayTag) tag);
		return null;
	}
	public DoubleArrayTag getDoubleArray(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof DoubleArrayTag) return ((DoubleArrayTag) tag);
		return null;
	}
	public BoolArrayTag getBooleanArray(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof BoolArrayTag) return ((BoolArrayTag) tag);
		return null;
	}
	public TagArray getTagArray(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof TagArray) return ((TagArray) tag);
		return null;
	}
	public CharArrayTag getCharArray(String name) {
		Tag<?> tag = get(name);
		if (tag instanceof CharArrayTag) return ((CharArrayTag) tag);
		return null;
	}


	@Override
	public Tag<?> remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Tag<?>> m) {
		map.putAll(m);
	}


	public Tag<?> get(String name) {
		return map.get(name);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean contains(Tag<?> other) {
		return map.containsValue(other);
	}

	@Override
	public Map<String, Tag<?>> getValue() {
		return map;
	}

	@Override
	public void setValue(Map<String, Tag<?>> map) {
		this.map = map;
	}


	@Override
	protected byte[] getPayload() {
		//array is initialized:
		byte[] bytes = new byte[payloadSize()];
		int offset = 0;
		for (Map.Entry<String, Tag<?>> entry: map.entrySet()){
			byte[] entryTag = entry.getValue().toByteArray(entry.getKey());
			System.arraycopy(entryTag, 0, bytes, offset, entryTag.length);
			offset += entryTag.length;
		}
		//zero at the end remains due to the presence of one empty cell after the array is initialized
		return bytes;
	}

	@Override
	public int payloadSize() {
		int size = 1;//null-terminated
		for (Map.Entry<String, Tag<?>> entry: map.entrySet()){
			size += entry.getValue().tagSize(entry.getKey());
		}

		return size;
	}


	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Tag<?> get(Object key) {
		return map.get(key);
	}

	public boolean containsKey(String key) {
		return map.containsKey(key);
	}


	public Tag<?> remove(String key) {
		return map.remove(key);
	}

	public boolean containsValue(Tag<?> value) {
		return map.containsValue(value);
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public Collection<Tag<?>> values() {
		return map.values();
	}

	public Set<Map.Entry<String, Tag<?>>> entrySet() {
		return map.entrySet();
	}

	public Tag<?> getOrDefault(String key, Tag<?> defaultValue) {
		return map.getOrDefault(key, defaultValue);
	}

	public Tag<?> putIfAbsent(String key, Tag<?> value) {
		return map.putIfAbsent(key, value);
	}

	public boolean remove(String key, Tag<?> value) {
		return map.remove(key, value);
	}

	public boolean replace(String key, Tag<?> oldValue, Tag<?> newValue) {
		return map.replace(key, oldValue, newValue);
	}

	public Tag<?> replace(String key, Tag<?> value) {
		return map.replace(key, value);
	}

	public Tag<?> computeIfAbsent(String key, Function<String, ? extends Tag<?>> mappingFunction) {
		return map.computeIfAbsent(key, mappingFunction);
	}

	public Tag<?> computeIfPresent(String key, BiFunction<String, ? super Tag<?>, ? extends Tag<?>> remappingFunction) {
		return map.computeIfPresent(key, remappingFunction);
	}

	public Tag<?> compute(String key, BiFunction<String, ? super Tag<?>, ? extends Tag<?>> remappingFunction) {
		return map.compute(key, remappingFunction);
	}

	public Tag<?> merge(String key, Tag<?> value, BiFunction<? super Tag<?>, ? super Tag<?>, ? extends Tag<?>> remappingFunction) {
		return map.merge(key, value, remappingFunction);
	}



	@Override
	public String toGenericString(String name) {
		return getClass().getSimpleName() + "(\"" + name + "\")" + (getFlag()?'*':"") + " = " + generic();
	}

	private String generic() {
		Iterator<Map.Entry<String,Tag<?>>> i = entrySet().iterator();
		if (! i.hasNext())
			return "{}";

		StringBuilder sb = new StringBuilder();
		sb.append('{').append('\n');
		while (true) {
			Map.Entry<String,Tag<?>> e = i.next();
			String key = e.getKey();
			Tag<?> value = e.getValue();
			sb.append(value.toGenericString(key));
			if (! i.hasNext())
				return sb.append('\n').append('}').toString();
			sb.append('\n');
		}
	}
}

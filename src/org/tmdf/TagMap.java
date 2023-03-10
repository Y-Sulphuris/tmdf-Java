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
public final class TagMap extends CollectionTag<HashMap<String, Tag<?>>> {
	private HashMap<String,Tag<?>> map;

	public TagMap(HashMap<String, Tag<?>> map) {
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
	public HashMap<String, Tag<?>> getValue() {
		return map;
	}

	@Override
	public void setValue(HashMap<String, Tag<?>> map) {
		this.map = map;
	}

	@Override
	public Tag<HashMap<String, Tag<?>>> clone() {
		return new TagMap((HashMap<String, Tag<?>>) map.clone());
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

	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	public void putAll(Map<String, ? extends Tag<?>> m) {
		map.putAll(m);
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

	public void forEach(BiConsumer<String, ? super Tag<?>> action) {
		map.forEach(action);
	}

	public void replaceAll(BiFunction<String, ? super Tag<?>, ? extends Tag<?>> function) {
		map.replaceAll(function);
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

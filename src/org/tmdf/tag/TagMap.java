package org.tmdf.tag;

import org.tmdf.TmdfUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class TagMap extends CollectionTag<Map<String, Tag<?>>> {
	private Map<String,Tag<?>> map;

	public TagMap(Map<String, Tag<?>> map) {
		this.map = map;
	}
	public TagMap() {
		this.map = new HashMap<>();
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
	public Map<String, Tag<?>> getValue() {
		return map;
	}

	@Override
	public void setValue(Map<String, Tag<?>> map) {
		this.map = map;
	}

	@Override
	public Tag<Map<String, Tag<?>>> clone() {
		return new TagMap(map);
	}

	@Override
	protected byte[] getPayload() {
		byte[] bytes = new byte[0];
		for (Map.Entry<String, Tag<?>> entry: map.entrySet()){
			bytes = TmdfUtils.sum(bytes,entry.getValue().toByteArray(entry.getKey()));
		}
		bytes = TmdfUtils.sum(bytes,new byte[]{0});
		return bytes;
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




}

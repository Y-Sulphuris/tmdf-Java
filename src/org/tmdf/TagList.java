package org.tmdf;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * 	TagList: 9<br>
 * 	Payload: unordered array of tags without a name (name = ""). Always null-terminated (size not defined)<br>
 * 	Flag: none
 */
public final class TagList extends CollectionTag<ArrayList<Tag<?>>> {
	private ArrayList<Tag<?>> list;

	public TagList(ArrayList<Tag<?>> list) {
		this.list = list;
	}
	public TagList() {
		this.list = new ArrayList<>();
	}
	public TagList(Tag<?>... tags) {
		this.list = new ArrayList<>();
		list.addAll(Arrays.asList(tags));
	}
	public TagList add(Tag<?> element) {
		list.add(element);
		return this;
	}
	public TagList remove(Tag<?> element) {
		list.remove(element);
		return this;
	}
	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean contains(Tag<?> other) {
		return list.contains(other);
	}

	@Override
	public ArrayList<Tag<?>> getValue() {
		return list;
	}

	@Override
	public void setValue(ArrayList<Tag<?>> value) {
		this.list = value;
	}




	/*
			for (int i = 0; i < entryTag.length; i++) {
				bytes[offset+i] = entryTag[i];
			}
	*/
	@Override
	protected byte[] getPayload() {
		byte[] bytes = new byte[payloadSize()]; //includes a zero at the end, so you do not need to add it separately
		int offset = 0;
		for (Tag<?> tag: list){
			byte[] current = tag.toByteArray("");
			System.arraycopy(current, 0, bytes, offset, current.length);
			offset += current.length;
		}
		//null-terminated
		return bytes;
	}

	@Override
	public int payloadSize() {
		int size = 1;//1 - zero at the end

		for (Tag<?> tag: list){
			size += tag.tagSize("");
		}
		return size;
	}


	public boolean isEmpty() {
		return list.isEmpty();
	}

	public Iterator<Tag<?>> iterator() {
		return list.iterator();
	}

	public Tag<?>[] toArray() {
		return (Tag<?>[]) list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	public boolean addAll(Collection<? extends Tag<?>> c) {
		return list.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends Tag<?>> c) {
		return list.addAll(index, c);
	}

	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	public void replaceAll(UnaryOperator<Tag<?>> operator) {
		list.replaceAll(operator);
	}

	public void sort(Comparator<? super Tag<?>> c) {
		list.sort(c);
	}

	public Tag<?> get(int index) {
		return list.get(index);
	}

	public Tag<?> set(int index, Tag<?> element) {
		return list.set(index, element);
	}

	public void add(int index, Tag<?> element) {
		list.add(index, element);
	}

	public Tag<?> remove(int index) {
		return list.remove(index);
	}

	public int indexOf(Tag<?> o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Tag<?> o) {
		return list.lastIndexOf(o);
	}

	public ListIterator<Tag<?>> listIterator() {
		return list.listIterator();
	}

	public ListIterator<Tag<?>> listIterator(int index) {
		return list.listIterator(index);
	}

	public List<Tag<?>> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	public Spliterator<Tag<?>> spliterator() {
		return list.spliterator();
	}

	public boolean removeIf(Predicate<? super Tag<?>> filter) {
		return list.removeIf(filter);
	}

	public Stream<Tag<?>> stream() {
		return list.stream();
	}

	public Stream<Tag<?>> parallelStream() {
		return list.parallelStream();
	}

	public void forEach(Consumer<? super Tag<?>> action) {
		list.forEach(action);
	}
}

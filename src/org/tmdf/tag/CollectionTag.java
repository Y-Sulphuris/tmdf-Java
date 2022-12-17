package org.tmdf.tag;

public abstract class CollectionTag<T> extends Tag<T>{
	public abstract int size();
	public abstract void clear();
	public abstract boolean contains(Tag<?> other);
}

package org.tmdf;

public abstract class CollectionTag<T> extends Tag<T>  {
	public abstract int size();
	public abstract void clear();
	public abstract boolean contains(Tag<?> other);

	@Override
	protected boolean getFlag() {
		return false;//flag is unused
	}

	@Override
	public final String toString() {
		return "{" + size() + " entities}";
	}
}

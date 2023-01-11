package org.tmdf;

public abstract class ArrayTag<A,T> extends Tag<A>{
	public abstract T get(int index);
	public abstract void set(int index, T x);
	public abstract int length();

	public abstract String toContentString();
	@Override
	public final String toString() {
		return new StringBuilder("[").append(length()).append(" entities]").toString();
	}
}

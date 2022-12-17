package org.tmdf.tag;

public abstract class ArrayTag<A,T> extends Tag<A>{
	public abstract T get(int index);
	public abstract void set(int index, T x);
	public abstract int length();

}

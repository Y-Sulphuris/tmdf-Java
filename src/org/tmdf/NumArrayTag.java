package org.tmdf;

public abstract class NumArrayTag<A, T extends Number> extends ArrayTag<A, T> {
	public final boolean isSigned() {
		return !getFlag();
	}

	public final NumArrayTag<A, T> setSigned(boolean signed) {
		setFlag(!signed);
		return this;
	}
}

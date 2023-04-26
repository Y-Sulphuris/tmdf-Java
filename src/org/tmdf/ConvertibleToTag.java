package org.tmdf;

public interface ConvertibleToTag {
	Tag<?> toTag();
	default int sizeInTmdf() {
		return toTag().tagSize("");
	}
	default int sizeInTmdf(String name) {
		return toTag().tagSize(name);
	}
	default byte[] toTmdfByteArray(String name) {
		return toTag().toByteArray(name);
	}
}

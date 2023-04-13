package org.tmdf;

public interface ConvertibleToTag {
    Tag<?> toTag();

    default int sizeInTmdf() {
        return this.toTag().tagSize("");
    }

    default int sizeInTmdf(String name) {
        return this.toTag().tagSize(name);
    }

    default byte[] toTmdfByteArray(String name) {
        return this.toTag().toByteArray(name);
    }
}

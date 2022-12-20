package org.tmdf;

import java.util.Objects;

public final class NamedTag {
    String name;

    Tag<?> tag;

    public String getName() {
        return name;
    }

    public Tag<?> getTag() {
        return tag;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public void setTag(Tag<?> tag) {
        this.tag = Objects.requireNonNull(tag);
    }


    public NamedTag(String name, Tag<?> tag) {
        this.name = Objects.requireNonNull(name);
        this.tag = Objects.requireNonNull(tag);
    }
}

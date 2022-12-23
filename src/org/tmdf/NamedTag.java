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
    public NamedTag(String name, Object obj) {
        this(name,Tag.wrap(obj));
    }

    public Object getValue() {
        return tag.getValue();
    }

    public boolean getFlag() {
        return tag.getFlag();
    }
    public void setFlag(boolean flag) {
        tag.setFlag(flag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedTag namedTag = (NamedTag) o;
        return Objects.equals(name, namedTag.name) && Objects.equals(tag, namedTag.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tag);
    }

    public String toGenericString() {
        return tag.toGenericString(name);
    }
    public byte[] toByteArray() {
        return tag.toByteArray(name);
    }
}

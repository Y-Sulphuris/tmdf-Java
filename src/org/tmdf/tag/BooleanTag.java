package org.tmdf.tag;

public final class BooleanTag extends Tag<Boolean>{
	private boolean value = false;

	public static BooleanTag of(boolean value) {
		return value ? TRUE : FALSE;
	}

	public static final BooleanTag TRUE = new BooleanTag(true);
	public static final BooleanTag FALSE = new BooleanTag(false);

	private BooleanTag(boolean value) {
		this.value = value;
	}
	public BooleanTag() {

	}

	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	public Tag<Boolean> clone() {
		return new BooleanTag(value);
	}

	@Override
	protected byte[] getPayload() {
		return new byte[]{(byte) (value?1:0)};
	}


	public int compareTo(Boolean b) {
		return getValue().compareTo(b);
	}
}

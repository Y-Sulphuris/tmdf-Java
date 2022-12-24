package org.tmdf;

/**
 * 	BoolTag: 7<br>
 * 	Payload: none<br>
 * 	Flag: boolean value of tag (1 if true, 0 if false)
 */
public final class BoolTag extends Tag<Boolean>{
	public static BoolTag of(boolean value) {
		return value ? TRUE : FALSE;
	}

	public static final BoolTag TRUE = new BoolTag(true);
	public static final BoolTag FALSE = new BoolTag(false);

	private BoolTag(boolean value) {
		super.setFlag(value);
	}

	@Override
	public Boolean getValue() {
		return getFlag();
	}

	@Override
	@Deprecated
	public void setValue(Boolean value) {
		//Immutable
	}
	@Override
	protected BoolTag setFlag(boolean flag) {
		return of(flag);
	}

	@Override
	public BoolTag clone() {
		return this;
	}

	@Override
	protected byte[] getPayload() {
		return new byte[0];
	}

	@Override
	public int payloadSize() {
		return 0;
	}


	public int compareTo(Boolean b) {
		return getValue().compareTo(b);
	}
}

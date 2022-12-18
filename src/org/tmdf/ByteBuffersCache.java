package org.tmdf;

import java.nio.ByteBuffer;

/**
 * to avoid creating too many ByteBuffer
 */
public final class ByteBuffersCache {
	public static final ByteBuffer bb2 = ByteBuffer.allocate(2);
	public static final ByteBuffer bb4 = ByteBuffer.allocate(4);
	public static final ByteBuffer bb8 = ByteBuffer.allocate(8);

	public static void clearAll() {
		bb2.clear();
		bb4.clear();
		bb8.clear();
	}
}

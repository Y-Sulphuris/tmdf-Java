package org.tmdf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.tmdf.ByteBuffersCache.bb2;
import static org.tmdf.ByteBuffersCache.bb4;

public final class TmdfUtils {
	private TmdfUtils(){
		throw new UnsupportedOperationException();
	}
	private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8; //Charset.forName("UTF-8");
	public static byte[] stringToTMDFNameByteArray(String str) {
		if (str.length()>255) {
			throw new IllegalArgumentException();
		}
		byte length = (byte)str.length();
		byte[] bytes = new byte[length+1];
		bytes[0] = length;

		byte[] utfStr = encodeUTF8(str);

		System.arraycopy(utfStr, 0, bytes, 1, bytes.length - 1);

		return bytes;
	}

	public static byte[] encodeUTF8(String str){
		return checkUTF8(str).getBytes(UTF8_CHARSET);
	}
	public static String decodeUTF8(byte[] bytes) {
		return new String(bytes, UTF8_CHARSET);
	}

	public static char[] sum(char[] a, char[] b) {
		char[] result = new char[a.length+ b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}
	public static byte[] sum(byte[] a, byte[] b) {
		byte[] result = new byte[a.length+ b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}
	public static byte[] sum(byte[]... arrays) {
		byte[] last = arrays[0];
		for (int i = 1; i < arrays.length; i++) {
			last = sum(last,arrays[i]);
		}
		return last;
	}

	public static String checkUTF8(String str) {
		if (str == null) str = "";
		if(str.getBytes(StandardCharsets.UTF_8).length != str.length()) {
			throw new IllegalArgumentException();
		}
		return str;
	}


	public static byte[] intToByteArray(int x) {
		bb4.putInt(0,x);
		return new byte[]{bb4.get(0),bb4.get(1),bb4.get(2),bb4.get(3)};
	}
	public static int byteArrayToInt(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			bb4.put(i,bytes[i]);
		}
		return bb4.getInt(0);
	}


	public static int TMDFNameByteLength(String name) {
		name = checkUTF8(name);
		return name.length()+1;
	}
}


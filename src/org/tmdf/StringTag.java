package org.tmdf;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.stream.IntStream;

public abstract class StringTag extends Tag<String> implements CharSequence{

	public static StringTag wrapString(String s) {
		if (s.contains("\0")) throw new IllegalArgumentException("String tag cannot contains null chars, use CharArrayTag");
		if (s.getBytes(StandardCharsets.UTF_8).length == s.length()) {
			return new StringUTF8Tag(s);
		} else return new StringUTF16Tag(s);
	}


	private String value;
	public StringTag(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		if (value.contains("\0")) throw new IllegalArgumentException("Tag string is null-terminated");
		this.value = value;
	}


	public int length() {
		return value.length();
	}

	public boolean isEmpty() {
		return value.isEmpty();
	}

	public char charAt(int index) {
		return value.charAt(index);
	}

	public int codePointAt(int index) {
		return value.codePointAt(index);
	}

	public int codePointBefore(int index) {
		return value.codePointBefore(index);
	}

	public int codePointCount(int beginIndex, int endIndex) {
		return value.codePointCount(beginIndex, endIndex);
	}

	public int offsetByCodePoints(int index, int codePointOffset) {
		return value.offsetByCodePoints(index, codePointOffset);
	}

	public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
		value.getChars(srcBegin, srcEnd, dst, dstBegin);
	}


	public boolean contentEquals(StringBuffer sb) {
		return value.contentEquals(sb);
	}

	public boolean contentEquals(CharSequence cs) {
		return value.contentEquals(cs);
	}

	public boolean equalsIgnoreCase(StringTag anotherString) {
		return value.equalsIgnoreCase(anotherString.value);
	}

	public int compareTo(StringTag anotherString) {
		return value.compareTo(anotherString.value);
	}

	public int compareToIgnoreCase(StringTag str) {
		return value.compareToIgnoreCase(str.value);
	}

	public boolean regionMatches(int toffset, StringTag other, int ooffset, int len) {
		return value.regionMatches(toffset, other.value, ooffset, len);
	}

	public boolean regionMatches(boolean ignoreCase, int toffset, StringTag other, int ooffset, int len) {
		return value.regionMatches(ignoreCase, toffset, other.value, ooffset, len);
	}

	public boolean startsWith(String prefix, int toffset) {
		return value.startsWith(prefix, toffset);
	}

	public boolean startsWith(String prefix) {
		return value.startsWith(prefix);
	}

	public boolean endsWith(String suffix) {
		return value.endsWith(suffix);
	}

	public int indexOf(int ch) {
		return value.indexOf(ch);
	}

	public int indexOf(int ch, int fromIndex) {
		return value.indexOf(ch, fromIndex);
	}

	public int lastIndexOf(int ch) {
		return value.lastIndexOf(ch);
	}

	public int lastIndexOf(int ch, int fromIndex) {
		return value.lastIndexOf(ch, fromIndex);
	}

	public int indexOf(String str) {
		return value.indexOf(str);
	}

	public int indexOf(String str, int fromIndex) {
		return value.indexOf(str, fromIndex);
	}

	public int lastIndexOf(String str) {
		return value.lastIndexOf(str);
	}

	public int lastIndexOf(String str, int fromIndex) {
		return value.lastIndexOf(str, fromIndex);
	}

	public String substring(int beginIndex) {
		return value.substring(beginIndex);
	}

	public String substring(int beginIndex, int endIndex) {
		return value.substring(beginIndex, endIndex);
	}

	public CharSequence subSequence(int beginIndex, int endIndex) {
		return value.subSequence(beginIndex, endIndex);
	}

	public String concat(String str) {
		return value.concat(str);
	}

	public String replace(char oldChar, char newChar) {
		return value.replace(oldChar, newChar);
	}

	public boolean matches(String regex) {
		return value.matches(regex);
	}

	public boolean contains(CharSequence s) {
		return value.contains(s);
	}

	public String replaceFirst(String regex, String replacement) {
		return value.replaceFirst(regex, replacement);
	}

	public String replaceAll(String regex, String replacement) {
		return value.replaceAll(regex, replacement);
	}

	public String replace(CharSequence target, CharSequence replacement) {
		return value.replace(target, replacement);
	}

	public String[] split(String regex, int limit) {
		return value.split(regex, limit);
	}

	public String[] split(String regex) {
		return value.split(regex);
	}

	public String toLowerCase(Locale locale) {
		return value.toLowerCase(locale);
	}

	public String toLowerCase() {
		return value.toLowerCase();
	}

	public String toUpperCase(Locale locale) {
		return value.toUpperCase(locale);
	}

	public String toUpperCase() {
		return value.toUpperCase();
	}

	public String trim() {
		return value.trim();
	}

	public char[] toCharArray() {
		return value.toCharArray();
	}

	public String intern() {
		return value.intern();
	}

	public IntStream chars() {
		return value.chars();
	}

	public IntStream codePoints() {
		return value.codePoints();
	}


	@Override
	public final String toString() {
		return '"'+super.toString()+'"';
	}
}

import org.tmdf.tag.*;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		DoubleTag doubleTag = new DoubleTag(Double.POSITIVE_INFINITY);

		TagMap map = new TagMap();
		map.put("byte",new ByteTag((byte)-1));
		map.put("abc",new TagList(new ByteTag((byte)8),new IntArrayTag(5,5,5)));
		map.put("str",new StringUTF8Tag("the name of this tmdf map"));
		map.put("str2",new StringUTF16Tag("я админ здрасьте"));
		map.put("off",new TagMap()
			.put("one",new DoubleTag(1))
			.put("doubleDich",doubleTag));
		byte[] bytes = doubleTag.toByteArray("map");

		System.out.println(doubleTag);
		System.out.println(Arrays.toString(bytes));
		System.out.println(new TagReader(bytes).nextUnnamedTag());
	}
}

import org.tmdf.*;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		DoubleTag doubleTag = new DoubleTag(Double.POSITIVE_INFINITY);

		TagMap map = new TagMap();
		map.put("byte",new ByteTag((byte)-1).setSigned(false));
		map.put("abc",new TagList(new ByteTag((byte)8),IntArrayTag.of(5,5,5)));
		map.put("str",new StringUTF8Tag("the name of this tmdf map"));
		map.put("str2",new StringUTF16Tag("я админ здрасьте"));
		map.put("off",new TagMap()
			.put("one",new DoubleTag(1))
			.put("doubleDich",doubleTag));
		map.put("charArray",new CharArrayTag("abc\0\0\1"));
		map.put("array of bits", new BoolArrayTag(4).setToShort(true));
		map.put("x",new TagArray(new IntTag(3),new ByteTag(8)).setToShort(true));



		byte[] bytes = map.toByteArray("map");

		System.out.println(map);
		System.out.println(Arrays.toString(bytes));
		System.out.println(new TagReader(bytes).nextUnnamedTag());
		//



		System.out.println(bytes.length+" "+" "+map.tagSize("map"));

	}
}

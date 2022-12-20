import org.tmdf.*;
import org.tmdf.debug.Debugger;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		DoubleTag doubleTag = new DoubleTag(Double.POSITIVE_INFINITY);

		TagMap map = new TagMap();
		map.put("byte",new ByteTag((byte)-1).setSigned(false));
		map.put("abc",new TagList(new ByteTag((byte)8),IntArrayTag.of(5,5,5)));
		map.put("str",new StringUTF8Tag("the name of this map in UTF-8"));
		map.put("str2",new StringUTF16Tag("(◠‿◕)"));
		map.put("off",new TagMap()
			.put("one",new DoubleTag(1))
			.put("double_00",doubleTag));
		map.put("charArray",new CharArrayTag("abc\0\0\1"));
		map.put("array_of_bits", BoolArrayTag.of("0101_1000 ").setToShort(true));
		map.put("x",new TagArray(new IntTag(3),new ByteTag(8)).setToShort(true));



		byte[] bytes = map.toByteArray("map");

		System.out.println(map);
		System.out.println(Arrays.toString(bytes));
		System.out.println(new TagReader(bytes).nextUnnamedTag());
		//

		printAsBinary(bytes);

		System.out.println(Debugger.heapSizeOf(map,"map") +" -> "+ map.tagSize("map"));

	}



	static void printAsBinary(byte[] bytes) {
		BoolArrayTag bits = BoolArrayTag.of(bytes);
		for (int i = 0; i < bits.length(); i++) {
			if (i % 8 == 0 && i != 0) System.out.print('_');
			if (i % 64 == 0 && i != 0) System.out.print('_');
			System.out.print(bits.get(i)?1:0);
		}
		System.out.println();
	}
}

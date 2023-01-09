import org.tmdf.*;

import java.io.DataInputStream;
import java.io.InputStream;

public class Main {


	public static void main(String[] args) {
		TagMap map = new TagMap();
		map.put("the_one",new IntTag(5).setSigned(false));
		map.put("paper", new TagList()
			.add(ByteArrayTag.of(4,3,6))
			.add(ByteArrayTag.of(9,9,2))
			.add(TagArray.of(
				new DoubleTag(7.4),//[0]
				BoolTag.FALSE)//[1]
			)
		);
		map.put("name_of_paper",new StringUTF16Tag("declaration of Independence"));
		NamedTag nmap = map.setName("Source tag");
		System.out.println(nmap.toGenericString());
		byte[] data = nmap.toByteArray();

		System.out.println(new StringUTF16Tag("aaa").setName("abc").toGenericString());
		//System.out.println(parser.tagPayloadOffset(19,"Source tag/name_of_paper"));
		//System.out.println(parser.getNamedTag(9,"Source tag/paper").toGenericString());
		System.out.println(3 << 3);

	}

	/*public static void main(String[] args) {
		DoubleTag doubleTag = new DoubleTag(Double.POSITIVE_INFINITY);

		TagMap map = new TagMap();
		map.put("byte",new ByteTag((byte)-1).setSigned(false));
		map.put("abc",new TagList(new ByteTag((byte)8),IntArrayTag.of(5,5,5)));
		map.put("str",new StringUTF8Tag("the name of this map in UTF-8"));
		map.put("str2",new StringUTF16Tag("(◠‿◕)"));
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

		printAsBinary(bytes);

		System.out.println(Debugger.heapSizeOf(map,"map") +" -> "+ map.tagSize("map"));

	}*/



}

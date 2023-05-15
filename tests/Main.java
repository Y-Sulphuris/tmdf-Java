
import org.tmdf.TagMap;
import org.tmdf.TmdfName;
import org.tmdf.TmdfSerializable;

import java.io.*;

class TestClass implements TmdfSerializable {
	@TmdfName("v_int")
	int v_int = 6;
	@TmdfName("abc")
	byte abc = 10;
	@TmdfName("next")
	TestClass next;

	@Override
	public String toString() {
		return "TestClass{" +
				"v_int=" + v_int +
				", abc=" + abc +
				", next=" + next +
				'}';
	}
}
public class Main {


	public static void main(String[] args) throws IOException {
		TestClass test0 = new TestClass();
		test0.abc = 4;
		test0.next = new TestClass();
		TagMap map = test0.toTag();
		System.out.println(map.toGenericString("map"));
		TestClass test = new TestClass();
		test.load(map);
		System.out.println(test);
		//System.out.println(Tag.serializeObject(System.out).toGenericString("Object"));
		



		/*TagMap map = new TagMap();
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

		File file = nmap.dump("/streamTest.tmdf",false);
		System.out.println(TagReader.read(new DataInputStream(Files.newInputStream(file.toPath()))).toGenericString());*/
	}




}

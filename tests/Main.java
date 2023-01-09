import org.tmdf.*;

import java.io.*;
import java.nio.file.Files;

public class Main {


	public static void main(String[] args) throws IOException {
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

		File file = nmap.dump("/streamTest.tmdf",false);
		System.out.println(TagReader.read(new DataInputStream(Files.newInputStream(file.toPath()))).toGenericString());
	}




}

# tmdf-Java
TagMap Data Format specification

TagMap Data Format — hierarchical file format based on tags
The tag consists of several components:
------------------------------------

Special bit flag - [1 bit]<br>
TagType - [7 bit] (unsigned integer)<br>
TagNameLength - [1 byte] (unsigned one-byte integer value, max length is 255)<br>
TagName - [TagNameLength bytes] (UTF-8)<br>
PayLoad - (size depends on tag type)<br>

------------------------------------

There are several types of tags:
* All numbers use big endian *
* If Flag is marked as none, it is always 0 *
* Zero means the end of the collection or string if it is where the next tag ID is expected *
  <br><br>
  ByteTag: 1
  Payload: single-byte integer value (1 byte)
  Flag: true if unsigned, false if not

  ShortTag: 2
  Payload: two-byte integer value (2 bytes)
  Flag: true if unsigned, false if not

  IntTag: 3
  Payload: four-byte integer value (4 bytes)
  Flag: true if unsigned, false if not

  LongTag: 4
  Payload: eight-byte integer value (8 bytes)
  Flag: true if unsigned, false if not

  FloatTag: 5
  Payload: four-byte fractional value (4 bytes) — IEEE 754-2008
  Flag: none

  DoubleTag: 6
  Payload: eight-byte fractional value (8 bytes) — IEEE 754-2008
  Flag: none

  BoolTag: 7
  Payload: none
  Flag: boolean value of tag (1 if true, 0 if false)

  StringUTF8Tag: 8
  Payload: null-terminated array of single-byte characters in UTF-8 format. Always ends with '\00' (size in bytes equals string length + 1)
  Flag: none

  TagList: 9
  Payload: unordered array of tags without a name (name = ""). Always null-terminated (size not defined)
  Flag: none

  TagMap: 10
  Payload: unordered array of named tags. Always null-terminated (size not defined)
  Flag: none

  ByteArrayTag: 11
  Payload: an ordered array of one-byte integers. The first 4 bytes mean the length of the array (size in bytes = array size + 4)
  Flag: If true, all values are unsigned, else all values are signed

  ShortArrayTag: 12
  Payload: an ordered array of two-byte integers. The first 4 bytes mean the length of the array (size in bytes = array size * 2 + 4)
  Flag: If true, all values are unsigned, else all values are signed

  IntArrayTag: 13
  Payload: an ordered array of four-byte integers. The first 4 bytes mean the length of the array (size in bytes = array size * 4 + 4)
  Flag: If true, all values are unsigned, else all values are signed

  LongArrayTag: 14
  Payload: an ordered array of eight-byte integers. The first 4 bytes mean the length of the array (size in bytes = array size * 8 + 4)
  Flag: If true, all values are unsigned, else all values are signed

  FloatArrayTag: 15
  Payload: an ordered array of four-byte fractional IEEE 754-2008. The first 4 bytes mean the length of the array (size in bytes = array size * 4 + 4)
  Flag: none

  DoubleArrayTag: 16
  Payload: an ordered array of eight-byte fractional IEEE 754-2008. The first 4 bytes mean the length of the array (size in bytes = array size * 8 + 4)
  Flag: none

  BoolArrayTag: 17
  Payload: an ordered array of booleans (boolean is 1 bit). The first 4 bytes mean the 1/8 of length of the array (size in bytes = array size/8 + (4 or 2))
  The size of the array in bits is always a multiple of 8. If it is not, zero bits are added until the size is a multiple of 8
  Flag: If true, the size of the array is 2 bytes (no more than 65535*8). Else 4 bytes (does not exceed 2147483647*8)

  TagArray: 18
  Payload: an ordered array of unnamed tags (name = ""). Tags are contained in the array as a whole (Not only payload). The first 4 bytes mean the length of the array (size not defined)
  Flag: If true, the size of the array is 2 bytes (no more than 65535). Else 4 bytes (does not exceed 2147483647)

  StringUTF16Tag: 19
  Payload: null-terminated array of two-byte characters in UTF-16 format. Always ends with 2-bytes '\0000' (size in bytes equals string length * 2 + 2)
  Flag: none

  CharArrayTag: 20
  Payload: an ordered array of two-byte characters in UTF-16 format. The first 4 bytes mean the length of the array (size in bytes equals array size * 2 + 4)
  Flag: If true, the size of the array is 2 bytes (no more than 65535). Else 4 bytes (does not exceed 2147483647)
    * Use CharArrayTag if you need to put a null value there. In all other cases it is recommended to use strings (StringUTF8Tag or StringUTF16Tag) *


It is recommended to use TagMap as the root of the hierarchy in the file, but this requirement is not mandatory: any tag can be located at the root

Encoding/Decoding Example:

    TagMap("root") = {
        StringUTF8Tag("hello") = "hello world"
        ByteTag("number")* = 230
    }

The first tag is a TagMap, its ID is 10. Write 10 as the first byte.
Tag name - "root". It consists of four characters, so the next byte will be equal to 4.
Now we sequentially write each character by its number in UTF-8.
In our case it is "root" -> [114, 111, 111, 116]
The payload in a TagMap is a set of named tags, so the next byte will be the ID of the tag inside the TagMap. We have this StringUTF8Tag, whose ID is 8. This will be the next byte.
Next, we transfer the tag name in the same way: "hello" (length = 5) -> [5][104, 101, 108, 108, 111]
The StringUTF8Tag payload is similar to a regular tag name, but differs in that the string starts at the beginning of the load and ends with a null byte. Thus, theoretically, it can have any length, but cannot contain a character with code 0 inside it.
Our string contains the text "hello world" which we also read in sequence and then add '\00' to the end: "hello world" -> [104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100][0]
The payload is over, so we move on to writing the next tag. Here it is ByteTag, its ID is 1. But the flag (*) is set on the tag, which means that we need to switch the first bit in the tag ID: 1 byte in binary is [0,0,0,0, 0,0,0,1], when toggling the first bit, we get [1,0,0,0, 0,0,0,1], which is equivalent to -127. We write this value as follows.
Now write the tag name: "number" -> [6][110, 117, 109, 98, 101, 114]
Having set the flag, we have already determined the sign, now all that remains for us is to write the value in its pure form. The value is 230, this will be our next byte (this is -26 if read as a signed byte).
TagMap is over, so we need to close it. All collections are null-terminated, so we just add a [0] byte to the end
And then we got the following array: (one array, spaces and separating brackets in the middle are added for readability, as in all previous fittings) (all bytes are written as signed)

[10][4][114, 111, 111, 116] [8][5][104, 101, 108, 108, 111][104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100][0] [-127][6][110, 117, 109, 98, 101, 114][-26] [0]
(The order in which the tags are written inside the TagMap may differ, but here it does not matter, it differs from the TagArray)
(Another write option: [10][4][114, 111, 111, 116] [-127][6][110, 117, 109, 98, 101, 114][-26] [8][5][104, 101, 108, 108, 111][104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100][0] [0])

Now read these bytes to restore the map.
The first byte is 10, which means the root tag is TagMap. We read the name. The next byte means length, so after reading it we just read the [length] bytes as characters (reading next 4 times -> [114, 111, 111, 116] --UTF-8--> "root"), followed by the payload.
The TagMap payload is a set of tags, so the next byte will be an ID. Here we see 8, which means the tag type is StringUTF8Tag
We read the name in the same way: [5] -> reading next 5 times -> [104, 101, 108, 108, 111] -> "hello".
The StringUTF8 payload is a null-terminated string. So we just read each byte as a character until we get 0: 104, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100//null found!
Since we read them as single-byte characters, we will get "hello world" as the output.
You can start reading the next map tag.
On a signed read, the next 8 bytes will be -127, no such tag exists. Recall that in this case we must refer to the first bit. There is an easier way: if the ID is less than zero during sign reading, then the flag is set. We store the flag as true, then read the remaining 7 bits, or convert the 8-bit ID to unsigned, getting 1. That is, the next tag is a ByteTag.
Reading the name: [6] -> reading next 6 times -> [110, 117, 109, 98, 101, 114] -> "number".
What remains is the payload, which is a one-byte number. When reading the next byte, we get -23. However, we remember that the flag is set to true, and in integers this indicates that the byte is unsigned. So we should read it as unsigned, thus getting 230.
We read the next tag in the map, however, its ID is 0, indicating that the TagMap has ended.
Reading completed:

    TagMap("root") = {
        StringUTF8Tag("hello") = "hello world"
        ByteTag("number")* = 230
    }

As you can see, we got the same output as it was originally, which means that the encoding and decoding works correctly.

Here is a more complex example to test your implementation:

    [10, 10, 83, 111, 117, 114, 99, 101, 32, 116, 97, 103, 9, 5, 112, 97, 112, 101, 114, 11, 0, 0, 0, 0, 3, 4, 3, 6, 11, 0, 0, 0, 0, 3, 9, 9, 2, -110, 0, 0, 2, 6, 0, 64, 29, -103, -103, -103, -103, -103, -102, 7, 0, 0, 19, 13, 110, 97, 109, 101, 95, 111, 102, 95, 112, 97, 112, 101, 114, 0, 100, 0, 101, 0, 99, 0, 108, 0, 97, 0, 114, 0, 97, 0, 116, 0, 105, 0, 111, 0, 110, 0, 32, 0, 111, 0, 102, 0, 32, 0, 73, 0, 110, 0, 100, 0, 101, 0, 112, 0, 101, 0, 110, 0, 100, 0, 101, 0, 110, 0, 99, 0, 101, 0, 0, -125, 7, 116, 104, 101, 95, 111, 110, 101, 0, 0, 0, 5, 0]

    (variant with delimiter brackets for readability:
    [10][10][83, 111, 117, 114, 99, 101, 32, 116, 97, 103] [9][5][112, 97, 112, 101, 114] [11][0][0, 0, 0, 3][4, 3, 6][11][0][0, 0, 0, 3][9, 9, 2][-110][0][0, 2][6][0][64, 29, -103, -103, -103, -103, -103, -102][7][0][0] [19][13][110, 97, 109, 101, 95, 111, 102, 95, 112, 97, 112, 101, 114][0, 100,, 0, 101,, 0, 99,, 0, 108,, 0, 97,, 0, 114,, 0, 97,, 0, 116,, 0, 105,, 0, 111,, 0, 110,, 0, 32,, 0, 111, 0, 102,, 0, 32,, 0, 73,, 0, 110,, 0, 100,, 0, 101,, 0, 112,, 0, 101,, 0, 110,, 0, 100,, 0, 101,, 0, 110,, 0, 99,, 0, 101][0, 0][-125][7][116, 104, 101, 95, 111, 110, 101][0, 0, 0, 5][0]
    )

As a result, we should get

	TagMap("Source tag") = {
		IntTag("the_one")* = 1
		TagList("paper") = {
			ByteArrayTag = [4,3,0]
			ByteArrayTag = [9,9,2]
			TagArray = [DoubleTag = 7.4, BoolTag = false]
		}
		StringUTF16Tag("name_of_paper") = "declaration of Independence"
	}

(order of tags in TagMap and TagList doesn't matter, but not in ArrayTag)
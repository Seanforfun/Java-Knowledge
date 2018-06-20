package ca.mcmaster.jvm.console;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 18, 2018 4:55:17 PM
 * @version 1.0
 */
public class ClassModifier {
	private static final int CONSTANT_POOL_COUNT_INDEX = 8;	//4 magic number and 4 version number.
	private static final int CONSTANT_UTF8_INFO = 1;	//Unicode in UTF-8, fixed size.
	//常量池中的11种常量，除了CONSTANT_UTF8_INFO都不是定长的。以下的每一个值是每个tag对应的常量长度。
	private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, 5, -1, 5, 9, 9, 3,3 ,5,5,5,5};
	private static final int u1 = 1;
	private static final int u2 = 2;
	private byte[] classByte;
	public ClassModifier(byte[] classByte){
		this.classByte = classByte;
	}
	public byte[] modifyUTF8Constant(String oldStr, String newStr){
		int cpc = getConstantPoolCount();	//常量池里项目的个数，u2
		int offset = CONSTANT_POOL_COUNT_INDEX + u2;	//此时指向常量池个数之后的下一个字节。
		for(int i = 0; i < cpc; i++){
			/**
			 * CONSTANT_Utf8_info {
			 *     u1 tag;
			 *	    u2 length;
			 *	    u1 bytes[length];
			 *	}
			 */
			//tag[1字节] 不同的取值，决定了其下info的结构不同
			int tag = ByteUtils.bytes2Int(classByte, offset, u1);	
			if(tag == CONSTANT_UTF8_INFO){
				// The value of the length item gives the number of bytes in the bytes array (not the length of the resulting string). The strings in the CONSTANT_Utf8_info structure are not null-terminated. 
				int len = ByteUtils.bytes2Int(classByte, offset + u1, u2);
				offset += (u1 + u2);
				String str = ByteUtils.bytes2String(classByte, offset, len);	//将常量表的内容取出来。
				if(str.equalsIgnoreCase(oldStr)){
					byte[] strBytes = ByteUtils.string2Bytes(newStr);
					byte[] strLen = ByteUtils.int2Bytes(newStr.length(), u2);
					// 将原本的位置的长度替换成新的String的字节长度，将内容替换成新的字符串的字节内容。
					classByte = ByteUtils.bytesReplace(classByte, offset - u2, u2, strLen);
					classByte = ByteUtils.bytesReplace(classByte, offset, len, strBytes);
					return classByte;
				}else{
					offset += len;	//如果要替换的内容和原来的一致，则直接移至下一个常量。
				}
			}else{
				offset += CONSTANT_ITEM_LENGTH[tag];
			}
		}
		return classByte;
	}
	private int getConstantPoolCount() {
		return ByteUtils.bytes2Int(classByte, CONSTANT_POOL_COUNT_INDEX, u2);
	}
}

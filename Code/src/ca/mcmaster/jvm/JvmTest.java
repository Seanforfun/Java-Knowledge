package ca.mcmaster.jvm;

import java.lang.reflect.Field;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 6, 2018 3:34:35 PM
 * @version 1.0
 */
public class JvmTest {
	private static final Long _1MB = (long) (1024 * 1024);
//	public static final sun.misc.Unsafe UNSAFE = sun.misc.Unsafe.getUnsafe();
	public static void main(String[] args) throws Exception{
		Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
		f.setAccessible(true);
		sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
		long allocateMemory = unsafe.allocateMemory(_1MB);
		System.out.println(Long.toHexString(allocateMemory));
		unsafe.freeMemory(allocateMemory);
	}
}

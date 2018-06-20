package ca.mcmaster.jvm.console;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 19, 2018 6:41:44 PM
 * @version 1.0
 */
public class HackSystem {
	public static final InputStream in = System.in; 
	private static final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	public final static PrintStream out = new PrintStream(buffer);
	public final static PrintStream err = out;
	/**
	 * @Description:将ByteArrayOutputStream中的内容存储成String
	 * @Return: String
	 */
	public static String getBufferString(){
		return buffer.toString();
	}
	/**
	 * @Description:清空buffer的内容
	 * @Return: void
	 */
	public static void clearBuffer(){
		buffer.reset();
	}
	public static void setSecurityManager(final SecurityManager s){
		System.setSecurityManager(s);
	}
	public static SecurityManager getSecurityManager(){
		return System.getSecurityManager();
	}
	public static long currentTimeMillis(){
		return System.currentTimeMillis();
	}
	public static void arrayCopy(Object src, int srcPos, Object dest, int destPos, int length){
		System.arraycopy(src, srcPos, dest, destPos, length);
	}
	public static int identityHashCode(Object x){
		return System.identityHashCode(x);
	}
}

package ca.mcmaster.jvm.console;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 18, 2018 4:46:55 PM
 * @version 1.0
 */
public class HotSwapClassLoader extends ClassLoader {
	public HotSwapClassLoader(){
		super(HotSwapClassLoader.class.getClassLoader());
	}
	/**
	 * @Description:Define a class from a byte array.
	 * @Return: Class
	 */
	public Class loadByte(byte[] classByte){
		return defineClass(null, classByte, 0, classByte.length);
	}
}

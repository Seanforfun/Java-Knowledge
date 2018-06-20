package ca.mcmaster.jvm.console;

import java.lang.reflect.Method;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 19, 2018 7:03:18 PM
 * @version 1.0
 */
public class JavaClassExecutor {
	public static String executor(byte[] classByte){
		HackSystem.clearBuffer();
		ClassModifier cm = new ClassModifier(classByte);
		// 将类文件中对java/lang/System的引用转化成我们自己写的HashSystem，从而实现类似注入。
		byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "ca/mcmaster/jvm/console/HackSystem");
		HotSwapClassLoader loader = new HotSwapClassLoader();
		Class clazz = loader.loadByte(modiBytes);
		try {
			//通过反射获取main方法，并调用main方法。
			Method method = clazz.getMethod("main", new Class[]{String[].class});
			method.invoke(null, new String[]{null});
		} catch (Exception e) {
			e.printStackTrace(HackSystem.out);
		}
		return HackSystem.getBufferString();
	}
}

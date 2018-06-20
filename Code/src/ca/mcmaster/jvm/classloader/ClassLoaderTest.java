package ca.mcmaster.jvm.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 14, 2018 3:16:41 PM
 * @version 1.0
 */
public class ClassLoaderTest extends ClassLoader{

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		String filename = name.substring(name.lastIndexOf('.') + 1) + ".class";
		InputStream is = getClass().getResourceAsStream(filename);
		if(is == null)
			return super.loadClass(name);
		try {
			byte[] b = new byte[is.available()];
			is.read(b);
			return defineClass(name, b, 0, b.length);
		} catch (IOException e) {
			throw new ClassNotFoundException();
		}
	}
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ClassLoaderTest classLoader = new ClassLoaderTest();
		Object object = classLoader.loadClass("ca.mcmaster.jvm.classloader.ClassLoaderTest").newInstance();		
		System.out.println(object.getClass());
		System.out.println(object instanceof ca.mcmaster.jvm.classloader.ClassLoaderTest);
	}
}

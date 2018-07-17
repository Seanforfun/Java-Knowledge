package ca.mcmaster.spring.classloader;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 16, 2018 4:45:24 PM
 * @version 1.0
 */
public class ClassloaderTest {
	public static void main(String[] args) {
		ClassLoader applicationClassLoader = Thread.currentThread().getContextClassLoader();
		System.out.println("Current classloader: " + applicationClassLoader);
		System.out.println("Parent classloader: " + applicationClassLoader.getParent());
		System.out.println("Boot classloader: " + applicationClassLoader.getParent().getParent());
	}
}

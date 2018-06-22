package ca.mcmaster.oopdesign.singleton;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 2:55:04 PM
 * @version 1.0
 */
public class Singleton1 {
	private Singleton1(){
	}
	//创建静态内部类,在类加载阶段就已经完成，不会造成线程安全问题。
	private static class SingletonFactory{
		private final static Singleton1 instance = new Singleton1();
	}
	public Singleton1 getInstance(){
		return SingletonFactory.instance;
	}
	 public Object readResolve() {  
		 return getInstance();  
	 }  
}

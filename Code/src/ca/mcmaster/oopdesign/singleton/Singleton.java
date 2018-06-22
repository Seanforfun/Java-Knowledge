package ca.mcmaster.oopdesign.singleton;

import java.io.Serializable;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 2:11:44 PM
 * @version 1.0
 */
public class Singleton implements Serializable{
	private static Singleton instance = null;
	private Singleton(){	//私有化构造器，这样保证了外部不会实例化该类。
	}
//	public static Singleton getInstance(){
//		if(null == instance){
//			instance =  new Singleton();	//如果是第一次初始化，则新建一个对象。否则直接返回对象。
//		}
//		return instance;
//	}
//	public static synchronized Singleton getInstance(){
//		if(null == instance){
//			instance =  new Singleton();
//		}
//		return instance;
//	}
	public static Singleton getInstance(){
		if(null == instance){
			synchronized (instance) {
				instance = new Singleton();
			}
		}
		return instance;
	}
	public Object readResolve() {  //实现序列化
        return instance;  
    }  
}

package ca.mcmaster.oopdesign.decorator;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 24, 2018 8:11:05 PM
 * @version 1.0
 */
public interface Sourcable {
	public default void method(){
		System.out.println("This is a method...");
	}
}

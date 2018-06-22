package ca.mcmaster.oopdesign.factorymethod.simplefactory;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 11:35:31 AM
 * @version 1.0
 */
public interface Sender {
	public default void send(){
		System.out.println("This is a common sender!");
	}
}

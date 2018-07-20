package ca.mcmaster.spring.aop.wiring;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 20, 2018 9:33:44 AM
 * @version 1.0
 */
public class Waiter {
	public void greetTo(String name){
		System.out.println("Waiter Greet to " + name);
	}
	public void serveTo(String name){
		System.out.println("Serve for " + name);
	}
}

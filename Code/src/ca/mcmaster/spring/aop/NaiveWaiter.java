package ca.mcmaster.spring.aop;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 3:10:41 PM
 * @version 1.0
 */
public class NaiveWaiter implements Waiter {
	@Override
	public void greetTo(String name) throws Exception {
		System.out.println("Greet to " + name);
		throw new RuntimeException(name +" said dirty words.");
	}
	@Override
	public void serve(String name) {
		System.out.println("serve to " + name);
	}
}

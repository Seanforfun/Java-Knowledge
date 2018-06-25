package ca.mcmaster.oopdesign.proxy;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 24, 2018 9:02:17 PM
 * @version 1.0
 */
public class Source implements Sourcable {
	@Override
	public void method() {
		System.out.println("This is a method...");
	}
}

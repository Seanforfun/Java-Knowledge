package ca.mcmaster.oopdesign.adapter;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 24, 2018 7:06:20 PM
 * @version 1.0
 */
public class Adapter extends Source implements Targetable {
	@Override
	public void method2() {
		System.out.println("This is method2...");
	}
}

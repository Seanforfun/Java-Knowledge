package ca.mcmaster.oopdesign.factorymethod.simplefactory;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 11:36:52 AM
 * @version 1.0
 */
public class SMSSender implements Sender {
	@Override
	public void send() {
		System.out.println("This is a SMS Sender!");
	}
}

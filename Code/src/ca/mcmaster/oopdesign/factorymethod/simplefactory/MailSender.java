package ca.mcmaster.oopdesign.factorymethod.simplefactory;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 11:39:20 AM
 * @version 1.0
 */
public class MailSender implements Sender {
	@Override
	public void send() {
		System.out.println("This is a Mail Sender!");
	}
}

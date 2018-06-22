package ca.mcmaster.oopdesign.factorymethod.factory;

import ca.mcmaster.oopdesign.factorymethod.simplefactory.MailSender;
import ca.mcmaster.oopdesign.factorymethod.simplefactory.Sender;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 12:21:27 PM
 * @version 1.0
 */
public class MailSenderFactory implements FactoryProvider {
	@Override
	public Sender produce() {
		return new MailSender();
	}
	public static void main(String[] args) {
		FactoryProvider factory = new SMSSenderFactory();
		Sender sender = factory.produce();
		sender.send();
	}
}

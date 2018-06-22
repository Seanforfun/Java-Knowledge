package ca.mcmaster.oopdesign.factorymethod.simplefactory;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 11:58:25 AM
 * @version 1.0
 */
public class SimpleMultipleSenderFactory {
	public Sender produceMailSender(){
		return new MailSender();
	}
	public Sender produceSMSSender(){
		return new SMSSender();
	}
	public static void main(String[] args) {
		SimpleMultipleSenderFactory factory = new SimpleMultipleSenderFactory();
		Sender mailSender = factory.produceMailSender();
		mailSender.send();
		Sender smsSender = factory.produceSMSSender();
		smsSender.send();
	}
}

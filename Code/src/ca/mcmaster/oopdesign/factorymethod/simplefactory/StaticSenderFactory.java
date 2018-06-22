package ca.mcmaster.oopdesign.factorymethod.simplefactory;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 12:05:21 PM
 * @version 1.0
 */
public class StaticSenderFactory {
	public static Sender produceSMSSender(){
		return new SMSSender();
	}
	public static Sender produceMailSender(){
		return new MailSender();
	}
	public static void main(String[] args) {
		Sender mailSender = StaticSenderFactory.produceMailSender();
		mailSender.send();
		Sender smsSender = StaticSenderFactory.produceSMSSender();
		smsSender.send();
	}
}

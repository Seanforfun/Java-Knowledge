package ca.mcmaster.oopdesign.factorymethod.simplefactory;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 11:40:21 AM
 * @version 1.0
 */
public class SenderFactory {
	public Sender produce(String type){
		if("mail".equals(type)){
			return new MailSender();
		}else if("sms".equals(type))
			return new SMSSender();
		else
			return new Sender() {
			};
	}
	
	public static void main(String[] args) {
		SenderFactory senderFactory = new SenderFactory();
		Sender sender = senderFactory.produce(null);
		sender.send();
		Sender mailSender = senderFactory.produce("mail");
		mailSender.send();
		Sender smsMail = senderFactory.produce("sms");
		smsMail.send();
	}
}

package ca.mcmaster.spring.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 12:27:02 PM
 * @version 1.0
 */
@Component("mailSender")
public class MailSender implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		//将容器注册
		this.applicationContext = applicationContext;
	}
	public void sendMail(String to){
		System.out.println("MailSender: Send a mail!");
		//创建一个发送事件
		MailSendEvent mailSendEvent = new MailSendEvent(this.applicationContext, to);
		applicationContext.publishEvent(mailSendEvent);
	}
}

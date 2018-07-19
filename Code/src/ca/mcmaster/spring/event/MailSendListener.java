package ca.mcmaster.spring.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 12:23:21 PM
 * @version 1.0
 */
@Configuration
public class MailSendListener implements ApplicationListener<MailSendEvent> {
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 * 对事件进行处理。
	 */
	@Override
	public void onApplicationEvent(MailSendEvent event) {
		System.out.println("MailSendListener: Send mail to " + event.getTo() + ".");
	}
}

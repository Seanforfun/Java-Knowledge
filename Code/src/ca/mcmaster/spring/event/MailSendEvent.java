package ca.mcmaster.spring.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 12:20:24 PM
 * @version 1.0
 */
public class MailSendEvent extends ApplicationContextEvent {
	private String to;
	public MailSendEvent(ApplicationContext source, String to) {
		super(source);
		this.to = to;
	}
	public String getTo() {
		return to;
	}
}

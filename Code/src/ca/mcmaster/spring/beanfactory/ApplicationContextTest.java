package ca.mcmaster.spring.beanfactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.mcmaster.spring.Customer;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 17, 2018 10:36:01 AM
 * @version 1.0
 */
public class ApplicationContextTest {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:beans.xml");
		Customer customer = (Customer) ctx.getBean("customer");
		System.out.println(customer.getName());
		System.out.println(ctx.isSingleton("customer"));
	}
}

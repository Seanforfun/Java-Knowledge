package ca.mcmaster.spring.beanfactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ca.mcmaster.spring.beanfactory.annotation.TestBeans;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 17, 2018 10:59:53 AM
 * @version 1.0
 */
public class AnnotationTest {
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestBeans.class);
//		Car car = ctx.getBean("myCar", Car.class);
//		car.setColor("GREY");
//		System.out.println(car.getColor());
	}
}

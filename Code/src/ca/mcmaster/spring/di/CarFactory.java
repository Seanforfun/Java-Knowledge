package ca.mcmaster.spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 17, 2018 3:05:42 PM
 * @version 1.0
 */
public class CarFactory {
	public Car getInstance(){
		Car car = new Car("bmw", "white");
		return car;
	}
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		Car car = (Car) ctx.getBean("bmw");
		System.out.println(car.getBrand());
	}
}

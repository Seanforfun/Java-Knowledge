package ca.mcmaster.spring.beanfactory.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import ca.mcmaster.spring.reflect.Car;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 17, 2018 11:00:06 AM
 * @version 1.0
 */
@Configuration
public class TestBeans {
	private String color;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Bean(name="myCar")
	public Car buildCar(){
		Car c = new Car();
		setColor("Yellow");
		return c;
	}
}

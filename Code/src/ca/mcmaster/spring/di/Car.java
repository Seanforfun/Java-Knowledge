package ca.mcmaster.spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 17, 2018 2:34:04 PM
 * @version 1.0
 */
public class Car {
	private String brand;
	private String color;
	public Car(String brand, String color){
		this.brand = brand;
		this.color = color;
	}
	public String getBrand() {
		return brand;
	}
	public String getColor() {
		return color;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml") ;
		Car car = (Car) ctx.getBean("car");
		System.out.println(car.getBrand());
	}
}

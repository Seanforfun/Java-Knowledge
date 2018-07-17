package ca.mcmaster.spring.reflect;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 16, 2018 4:26:06 PM
 * @version 1.0
 */
public class Car {
	private String color;
	private String brand;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	private void drive(){
		System.out.println("I am driving!");
	}
}

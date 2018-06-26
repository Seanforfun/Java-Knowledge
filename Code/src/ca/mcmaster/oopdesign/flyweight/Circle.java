package ca.mcmaster.oopdesign.flyweight;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 26, 2018 12:18:49 PM
 * @version 1.0
 */
public class Circle implements Shape {
	private String color;
	public Circle(String color) {
		this.color = color;
	}
	@Override
	public void draw() {
		System.out.println("Draw a "+ this.color + " circle.");
	}
}

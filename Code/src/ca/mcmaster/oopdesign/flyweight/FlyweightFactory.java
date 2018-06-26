package ca.mcmaster.oopdesign.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 26, 2018 12:23:32 PM
 * @version 1.0
 */
public class FlyweightFactory {
	private static Map<String, Shape> shapes;
	static{
		shapes = new HashMap<String, Shape>();
	}
	public static Shape getShape(String key){
		Shape shape = shapes.get(key);
		if(null == shape){
			shape = new Circle(key);
			shapes.put(key, shape);
		}
		return shape;
	}
	public static int getSum(){
		return shapes.size();
	}
	public static void main(String[] args) {
		Shape grey = FlyweightFactory.getShape("grey");
		grey.draw();
		System.out.println(grey);
		Shape grey1 = FlyweightFactory.getShape("grey");
		System.out.println(grey1);
		Shape red = FlyweightFactory.getShape("red");
		red.draw();
	}
}

package ca.mcmaster.spring.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 16, 2018 4:27:49 PM
 * @version 1.0
 */
public class ReflectTest {
	public static Car getCarInstance() throws ClassNotFoundException, Exception, SecurityException{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class<?> carClass = loader.loadClass("ca.mcmaster.spring.reflect.Car");
		Constructor<?> constructor = carClass.getConstructor(null);
		Car car = (Car) constructor.newInstance();
		Method setColorMethod = carClass.getDeclaredMethod("setColor", String.class);
		setColorMethod.invoke(car, "Grey");
		Method setBrandMethod = carClass.getDeclaredMethod("setBrand", String.class);
		setBrandMethod.invoke(car, "RAV4");
		return car;
	}
	public static void main(String[] args) throws ClassNotFoundException, SecurityException, Exception {
		Car car = ReflectTest.getCarInstance();
		System.out.println("Color: " + car.getColor());
		System.out.println("Brand: " + car.getBrand());
		Method method = Car.class.getDeclaredMethod("drive", null);
		method.setAccessible(true);
		method.invoke(car, null);
	}
}

package ca.mcmaster.oopdesign.builder;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 3:35:19 PM
 * @version 1.0
 */
public class CarDirector {
	public Car constructCar(CarBuilder builder){
		builder.buildWheel();
		builder.buildSkeleton();
		builder.buildEngine();
		return builder.buildCar();
	}
	
	public static void main(String[] args) {
		CarBuilder builder = new ConcreteBuilder();
		Car car = new CarDirector().constructCar(builder);
		System.out.println(car.getEngine());
		System.out.println(car.getSkeleton());
		System.out.println(car.getWheel());
	}
}

package ca.mcmaster.oopdesign.builder;
/**
 * @author SeanForFun
 * @date  Jun 22, 2018 3:28:56 PM
 * @Description 具体的builder类，对每一个实例对象的每个部件都有具体的装配过程。
 * 但是每个部分是独立的。
 * @version 1.0
 */
public class ConcreteBuilder implements CarBuilder {
	private Car car;
	public ConcreteBuilder(){
		car = new Car();
	}
	@Override
	public void buildEngine() {
		car.setEngine("set engine...");
	}
	@Override
	public void buildSkeleton() {
		car.setSkeleton("set skeleton...");
	}
	@Override
	public void buildWheel() {
		car.setWheel("set wheel...");
	}
	@Override
	public Car buildCar() {
		return this.car;
	}
}

package ca.mcmaster.oopdesign.builder;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 3:27:13 PM
 * @version 1.0
 */
public interface CarBuilder {
	public void buildEngine();
	public void buildSkeleton();
	public void buildWheel();
	public Car buildCar();
}

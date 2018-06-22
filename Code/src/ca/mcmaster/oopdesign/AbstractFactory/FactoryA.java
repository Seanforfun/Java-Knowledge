package ca.mcmaster.oopdesign.AbstractFactory;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 1:50:00 PM
 * @version 1.0
 */
public class FactoryA implements Factory {
	@Override
	public Product produceContainer() {
		return new ContainerProductA();
	}
	@Override
	public Product produceModule() {
		return new MouldProductA();
	}
}

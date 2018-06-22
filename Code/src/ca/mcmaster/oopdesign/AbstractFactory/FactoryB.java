package ca.mcmaster.oopdesign.AbstractFactory;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 1:50:57 PM
 * @version 1.0
 */
public class FactoryB implements Factory {
	@Override
	public Product produceContainer() {
		return new ContainerProductB();
	}
	@Override
	public Product produceModule() {
		return new MouldProductB();
	}
}

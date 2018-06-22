package ca.mcmaster.oopdesign.AbstractFactory;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 1:54:41 PM
 * @version 1.0
 */
public class Test {
	public static void main(String[] args) {
		Factory factoryA = new FactoryA();
		factoryA.produceContainer().show();
		factoryA.produceModule().show();
		Factory factoryB = new FactoryB();
		factoryB.produceContainer().show();
		factoryB.produceModule().show();
	}
}

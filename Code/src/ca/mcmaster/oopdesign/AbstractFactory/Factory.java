package ca.mcmaster.oopdesign.AbstractFactory;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 1:22:27 PM
 * @version 1.0
 * @Description: 定义工厂的抽象类
 */
public interface Factory {
	public Product produceContainer();
	public Product produceModule();
}

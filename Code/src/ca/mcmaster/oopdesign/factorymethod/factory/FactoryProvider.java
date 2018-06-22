package ca.mcmaster.oopdesign.factorymethod.factory;

import ca.mcmaster.oopdesign.factorymethod.simplefactory.Sender;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 12:18:40 PM
 * @version 1.0
 */
public interface FactoryProvider {
	public Sender produce();
}

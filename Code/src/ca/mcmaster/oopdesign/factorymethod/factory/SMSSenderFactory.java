package ca.mcmaster.oopdesign.factorymethod.factory;

import ca.mcmaster.oopdesign.factorymethod.simplefactory.SMSSender;
import ca.mcmaster.oopdesign.factorymethod.simplefactory.Sender;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 22, 2018 12:20:45 PM
 * @version 1.0
 */
public class SMSSenderFactory implements FactoryProvider {
	@Override
	public Sender produce() {
		return new SMSSender();
	}
}

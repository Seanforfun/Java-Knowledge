package ca.mcmaster.oopdesign.mediator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 15, 2018 12:12:12 PM
 * @version 1.0
 */
public class ConcreteMediator extends Mediator{
	@Override
	public void execute(String name, Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		super.map.get(name).self();
		method.invoke(super.map.get(name), null);
	}
	public static void main(String[] args) throws Exception {
		ConcreteMediator mediator = new ConcreteMediator();
		ColleagueA a = new ColleagueA();
		mediator.addColleague("a", a);
		ColleagueB b = new ColleagueB();
		b.setMediator(mediator);
		mediator.addColleague("b", b);
		b.out();
	}
}

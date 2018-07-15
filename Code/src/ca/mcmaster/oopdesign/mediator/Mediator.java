package ca.mcmaster.oopdesign.mediator;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 15, 2018 11:58:43 AM
 * @version 1.0
 */
public abstract class Mediator {
	protected HashMap<String, AbstractColleague> map = new HashMap<>();
	public abstract void execute(String name, Method method) throws Exception;
	public void addColleague(String name, AbstractColleague c){
		c.setMediator(this);
		map.put(name, c);
	}
}

package ca.mcmaster.spring.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 3:15:30 PM
 * @version 1.0
 */
public class GreetBeforeAdvice implements MethodBeforeAdvice {
	@Override
	public void before(Method arg0, Object[] args, Object arg2)
			throws Throwable {
		String name = (String) args[0];
		System.out.println("Hello Mr/Ms. " + name);
	}
}

package ca.mcmaster.spring.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 3:56:35 PM
 * @version 1.0
 */
public class GreetingAfterAdvice implements AfterReturningAdvice {
	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		String name = (String) args[0];
		System.out.println("Goodbye " + name);
	}
}

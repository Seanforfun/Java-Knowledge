package ca.mcmaster.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 4:01:40 PM
 * @version 1.0
 */
public class GreetingInterceptor implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] arguments = invocation.getArguments();
		String name = (String) arguments[0];
		System.out.println("Hi! " + name);
		invocation.proceed();
		System.out.println("Goodbye! " + name);
		return null;
	}
}

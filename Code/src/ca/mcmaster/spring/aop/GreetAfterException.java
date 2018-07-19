package ca.mcmaster.spring.aop;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 4:12:42 PM
 * @version 1.0
 */
public class GreetAfterException implements ThrowsAdvice {
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable{
		System.out.println("Get exception: " + ex.getMessage());
		System.out.println("Method: " + method.getName());
	}
}

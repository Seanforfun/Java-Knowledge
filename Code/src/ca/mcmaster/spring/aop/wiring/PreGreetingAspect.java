package ca.mcmaster.spring.aop.wiring;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 25, 2018 11:27:54 AM
 * @version 1.0
 */
@Aspect		//	Define this class as an aspect
public class PreGreetingAspect {
	// 定义了切点
	@Before("execution(* ca.mcmaster.spring.aop..*.greetTo(..))")
	public void beforeGreeting(){	//定义了增强的内容
		System.out.println("How do you do?");
	}
}

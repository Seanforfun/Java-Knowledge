package ca.mcmaster.spring.aop.wiring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 25, 2018 3:07:45 PM
 * @version 1.0
 */
@Aspect
public class TestAspect {
	@Around("execution(* greetTo(..))")
	public void jointPointAccess(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("------------Before----------------");
		pjp.proceed();
		System.out.println("------------After----------------");
	}
}

package ca.mcmaster.spring.aop.wiring;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.NameMatchMethodPointcut;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 20, 2018 11:33:42 AM
 * @version 1.0
 */
public class GreetingComposablePointcut {
	public Pointcut getIntersectionPointcut(){
		ComposablePointcut cp = new ComposablePointcut();
		Pointcut pt1 = new ControlFlowPointcut(WaiterDelegant.class, "service");
		NameMatchMethodPointcut pt2 = new NameMatchMethodPointcut();
		pt2.addMethodName("greetTo");
		cp.intersection((Pointcut)pt2).intersection(pt1);
		return cp;
	}
}

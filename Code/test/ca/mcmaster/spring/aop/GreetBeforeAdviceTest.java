package ca.mcmaster.spring.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 3:16:59 PM
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:beans.xml"})
public class GreetBeforeAdviceTest {
	@Test
	public void test() throws Exception {
		Waiter waiter = new NaiveWaiter();
		BeforeAdvice greetBeforeAdvice = new GreetBeforeAdvice();
		ProxyFactory pf = new ProxyFactory(waiter);
//		pf.setInterfaces(new Class[]{Waiter.class});
		pf.setInterfaces(waiter.getClass().getInterfaces());
		pf.addAdvice(greetBeforeAdvice);
		Waiter proxy = (Waiter) pf.getProxy();
		proxy.greetTo("Sean");
//		System.out.println(proxy);
	}
	@Autowired
	@Qualifier("waiter")
	private Waiter waiter;
	@Test
	public void xmlTest() throws Exception{
		waiter.greetTo("Seanforfun");
	}
}

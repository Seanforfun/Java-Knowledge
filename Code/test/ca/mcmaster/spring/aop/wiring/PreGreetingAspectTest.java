package ca.mcmaster.spring.aop.wiring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.mcmaster.spring.aop.NaiveWaiter;
import ca.mcmaster.spring.aop.Waiter;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 25, 2018 12:06:56 PM
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:beans.xml"})
public class PreGreetingAspectTest {
	@Autowired
	@Qualifier("target")
	private Waiter waiter;
	@Test
	public void test() throws Exception {
		NaiveWaiter waiter = new NaiveWaiter();
		AspectJProxyFactory factory = new AspectJProxyFactory();
		factory.setTarget(waiter);
		factory.addAspect(PreGreetingAspect.class);
		Waiter proxy = factory.getProxy();
		proxy.greetTo("Sean");
		proxy.serve("Sean");
	}
	
	@Test
	public void aspectJXmlTest() throws Exception{
		waiter.greetTo("Sean Xiao");
		waiter.serve("Sean Xiao");
	}
}

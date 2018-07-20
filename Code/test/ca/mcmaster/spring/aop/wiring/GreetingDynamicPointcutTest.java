package ca.mcmaster.spring.aop.wiring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 20, 2018 10:47:24 AM
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:beans.xml"})
public class GreetingDynamicPointcutTest {
	@Autowired
	@Qualifier("waiterProxy")
	private Waiter waiter;
	@Test
	public void test() {
		waiter.greetTo("Irene");
		waiter.greetTo("X.B");
	}
}

package ca.mcmaster.spring.aop.wiring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 20, 2018 10:03:52 AM
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:beans.xml"})
public class GreetingAdvisorTest {
	@Autowired
	@Qualifier("waiterProxy")
	private Waiter waiterProxy;
	@Autowired
	@Qualifier("sellerProxy")
	private Seller sellerProxy;
	@Test
	public void test() {
		System.out.println(waiterProxy);
		waiterProxy.greetTo("Sean");
		sellerProxy.greetTo("Seanforfun");
	}
}

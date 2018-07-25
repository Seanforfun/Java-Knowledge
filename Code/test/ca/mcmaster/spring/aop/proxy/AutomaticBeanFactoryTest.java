package ca.mcmaster.spring.aop.proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.mcmaster.spring.aop.wiring.Seller;
import ca.mcmaster.spring.aop.wiring.Waiter;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 24, 2018 10:23:39 PM
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:beans.xml"})
public class AutomaticBeanFactoryTest {
	@Autowired
	@Qualifier("waiterTarget")
	private Waiter waiter;
	@Autowired
	@Qualifier("sellerTarget")
	private Seller seller;
	@Test
	public void test(){
		waiter.greetTo("Seanforfun");
		seller.greetTo("Sean");
	}
	
	@Test
	public void advisorTest(){
		waiter.greetTo("Seanforfun");
		seller.greetTo("Sean");
	}
}

package ca.mcmaster.spring.di;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 18, 2018 2:19:26 PM
 * @version 1.0
 */
public class TestDemoTest {
	@Autowired
	@Qualifier("testDemo")
	private TestDemo testDemo;
	@Test
	public void test() {
		System.out.println(testDemo);
	}
}

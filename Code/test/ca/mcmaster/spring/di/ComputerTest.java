package ca.mcmaster.spring.di;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 18, 2018 9:16:21 AM
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:beans.xml"})
public class ComputerTest {
	@Autowired
	private Computer computer;
	@Test
	public void test() {
		computer.printNow();
		System.out.println(computer.getBrand());
		System.out.println(computer.getStudent().getAge());
		System.out.println(computer.getColorList());
		computer.printStudent();
	}
}

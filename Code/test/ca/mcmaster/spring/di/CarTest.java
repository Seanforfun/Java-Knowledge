package ca.mcmaster.spring.di;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 18, 2018 11:10:49 AM
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:beans.xml"})
public class CarTest {
//	@Autowired
//	private Car redCar;
	@Autowired
	@Qualifier("teslaCar")
	private Car car;
	@Test
	public void test() {
		System.out.println(car.getBrand());
	}
	
	@Test
	public void getMultipleInjectionTest(@Qualifier("teslaCar") Car car, @Qualifier Student student){
	}
	
	@Autowired
	public void getMultipleInjectionTestInner(@Qualifier("teslaCar") Car car, @Qualifier Student student){
		System.out.println(car.getBrand());
		System.out.println(student.getName());
	}
}

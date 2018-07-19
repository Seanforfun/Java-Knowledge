package ca.mcmaster.spring.di;

import org.apache.commons.dbcp.BasicDataSource;
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
public class DataSourceTest {
//	@Autowired
//	private Car redCar;
	@Autowired
	@Qualifier("dataSource")
	private BasicDataSource dataSource;
	@Test
	public void test() {
		System.out.println(dataSource.getDriverClassName());
	}
	
//	@Autowired
//	public void getMultipleInjectionTestInner(@Qualifier("teslaCar") Car car, @Qualifier Student student){
//		System.out.println(car.getBrand());
//		System.out.println(student.getName());
//	}
}

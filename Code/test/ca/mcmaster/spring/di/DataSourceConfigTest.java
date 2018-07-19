package ca.mcmaster.spring.di;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 9:23:51 AM
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:beans.xml"})
public class DataSourceConfigTest {
	@Autowired
	@Qualifier("dataSourceConfig")
	private DataSourceConfig dataSourceConfig;
	@Test
	public void test() {
		System.out.println(dataSourceConfig.getMaxTimeOut());
	}
}

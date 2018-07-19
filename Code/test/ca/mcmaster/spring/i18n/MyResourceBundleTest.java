package ca.mcmaster.spring.i18n;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 10:59:56 AM
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:beans.xml"})
public class MyResourceBundleTest {
	@Autowired
	@Qualifier("myResource")
	private MessageSource messageSource;
	@Test
	public void test() {
		String message = messageSource.getMessage("greeting.common", new Object[]{"Jenny", new GregorianCalendar().getTime()}, Locale.CANADA);
		System.out.println(message);
	}
}

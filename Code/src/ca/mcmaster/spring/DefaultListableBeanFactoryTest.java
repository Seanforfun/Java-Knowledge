package ca.mcmaster.spring;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 16, 2018 10:13:12 AM
 * @version 1.0
 */
public class DefaultListableBeanFactoryTest {
	public static void main(String[] args) {
		ClassPathResource resource = new ClassPathResource("beans.xml");
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(factory);
		xmlReader.loadBeanDefinitions(resource);
		Customer customer = (Customer) factory.getBean("customer");
		System.out.println(customer.getName());
	}
}

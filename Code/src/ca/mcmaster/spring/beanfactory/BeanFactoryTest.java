package ca.mcmaster.spring.beanfactory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import ca.mcmaster.spring.Customer;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 17, 2018 9:14:20 AM
 * @version 1.0
 */
public class BeanFactoryTest {
	public static void main(String[] args) {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resource = resolver.getResource("classpath:beans.xml");
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		BeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(resource);
		Customer customer = (Customer) factory.getBean("customer");
		System.out.println(customer.getName());
	}
}

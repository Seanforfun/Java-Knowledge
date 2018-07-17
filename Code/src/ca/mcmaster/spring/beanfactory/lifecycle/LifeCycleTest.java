package ca.mcmaster.spring.beanfactory.lifecycle;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 17, 2018 12:35:09 PM
 * @version 1.0
 */
public class LifeCycleTest {
	public static void main(String[] args) {
		Resource resource = new ClassPathResource("beans.xml");
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(resource);
		MyBean bean = (MyBean) beanFactory.getBean("myBean");
		bean.printName();
		beanFactory.destroySingletons();
	}
}

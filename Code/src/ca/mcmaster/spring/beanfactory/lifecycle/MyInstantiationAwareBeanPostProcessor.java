package ca.mcmaster.spring.beanfactory.lifecycle;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 17, 2018 12:29:24 PM
 * @version 1.0
 */
public class MyInstantiationAwareBeanPostProcessor implements
		InstantiationAwareBeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("[BeanPostProcessor]:Run postProcessBeforeInitialization");
		return bean;
	}
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("[BeanPostProcessor]:Run postProcessAfterInitialization");
		return bean;
	}
	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass,
			String beanName) throws BeansException {
		System.out.println("[InstantiationAwareBeanPostProcessor]:Run postProcessBeforeInstantiation");
		return null;
	}
	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName)
			throws BeansException {
		System.out.println("[InstantiationAwareBeanPostProcessor]:Run postProcessAfterInstantiation");
		return true;
	}
	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs,
			PropertyDescriptor[] pds, Object bean, String beanName)
			throws BeansException {
		System.out.println("[InstantiationAwareBeanPostProcessor]:Run postProcessPropertyValues");
		return pvs;
	}
}

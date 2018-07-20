package ca.mcmaster.spring.aop.wiring;

import java.lang.reflect.Method;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 20, 2018 9:38:05 AM
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GreetingAdvisor extends StaticMethodMatcherPointcutAdvisor {
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		return "greetTo".equals(method.getName());
	}
	@Override
	public ClassFilter getClassFilter() {
//		return super.getClassFilter();
		return new ClassFilter() {
			@Override
			public boolean matches(Class<?> clazz) {
				// 要匹配的类是不是clazz的子类。
				return Waiter.class.isAssignableFrom(clazz);
			}
		};
	}
}

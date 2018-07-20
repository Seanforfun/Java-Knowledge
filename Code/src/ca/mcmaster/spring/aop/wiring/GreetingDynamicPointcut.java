package ca.mcmaster.spring.aop.wiring;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 20, 2018 10:40:56 AM
 * @version 1.0
 */
public class GreetingDynamicPointcut extends DynamicMethodMatcherPointcut {
	private static final List<String> names;
	static{
		names = new ArrayList<>();
		names.add("Sean");
		names.add("Irene");
	}
	@Override
	public boolean matches(Method method, Class<?> targetClass, Object[] args) {
		System.out.println("对" + method.getName() + "进行动态检查");
		return names.contains((String)args[0]);
	}
	@Override
	public ClassFilter getClassFilter() {
		return new ClassFilter() {
			@Override
			public boolean matches(Class<?> clazz) {
				return Waiter.class.isAssignableFrom(clazz);
			}
		};
	}
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		System.out.println("对" + method.getName() + "进行静态检查");
		return "greetTo".equals(method.getName());
	}
}

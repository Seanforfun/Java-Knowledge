package ca.mcmaster.spring.proxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import ca.mcmaster.spring.di.Car;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 2:46:21 PM
 * @version 1.0
 */
public class CglibProxy implements MethodInterceptor {
	private Enhancer enhancer = new Enhancer();
	
	private Object getProxy(Class clazz){
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		Monitor.begin();
		Object ret = proxy.invokeSuper(obj, args);
		Monitor.end();
		return ret;
	}
	
	public static void main(String[] args) {
		CglibProxy cglibProxy = new CglibProxy();
		Car car = (Car) cglibProxy.getProxy(Car.class);
		car.run();
	}
}

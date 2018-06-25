package ca.mcmaster.oopdesign.proxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 24, 2018 10:13:11 PM
 * @version 1.0
 */
public class CglibProxyFactory<T> implements MethodInterceptor{
	private final T target;
	public CglibProxyFactory(T target){
		this.target = target;
	}
	@SuppressWarnings("unchecked")
	public T getProxyInstance(){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(this);
		return (T) enhancer.create();
	}
	@Override
	public Object intercept(Object arg0, Method method, Object[] arg2,
			MethodProxy arg3) throws Throwable {
		System.out.println("Advice before...");
		Object ret = method.invoke(target, arg2);
		System.out.println("Advice after...");
		return ret;
	}
	public static void main(String[] args) {
		CglibProxyFactory<Bird> factory = new CglibProxyFactory<>(new Bird());
		Bird instance = factory.getProxyInstance();
		instance.shout();
	}
}

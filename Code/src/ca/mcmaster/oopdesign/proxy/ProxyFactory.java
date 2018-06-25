package ca.mcmaster.oopdesign.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 24, 2018 9:14:57 PM
 * @version 1.0
 */
public class ProxyFactory<T> {
	private T target;
	public ProxyFactory(T target){
		this.target = target;
	}
	@SuppressWarnings("unchecked")
	public T getProxyInstance(){
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				beforeAdvice();
				Object ret = method.invoke(target, args);
				afterAdvice();
				return ret;
			}
		});
	}
	private void beforeAdvice(){
		System.out.println("Advice before...");
	}
	private void afterAdvice(){
		System.out.println("Advice after...");
	}
	public static void main(String[] args) {
		Sourcable target = new Source();
		ProxyFactory<Sourcable> factory = new ProxyFactory<Sourcable>(target);
		Sourcable instance = factory.getProxyInstance();
		System.out.println(target);
		System.out.println(instance);
		instance.method();
	}
}

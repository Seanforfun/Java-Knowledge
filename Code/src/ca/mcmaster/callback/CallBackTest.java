package ca.mcmaster.callback;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date May 29, 2018 12:01:44 PM
 * @version 1.0
 */
public class CallBackTest {
	private Dog d = new Dog();
	public DogInterface getProxy(){
		DogInterface p = (DogInterface) Proxy.newProxyInstance(Dog.class.getClassLoader(), new Class[]{DogInterface.class}, new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				return method.invoke(d, args);
			}
		});
		return p;
	}
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, Throwable {
		Map<Integer, InvocationHandler> m = new HashMap<Integer, InvocationHandler>();
		CallbackInterface cb = new CallbackInterface() {
		};
		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				return method.invoke(proxy, args);
			}
		};
		m.put(1, handler);
		m.get(1).invoke(cb, cb.getClass().getMethod("CallbackHandler", Integer.class), new Integer[]{1});
	}
}

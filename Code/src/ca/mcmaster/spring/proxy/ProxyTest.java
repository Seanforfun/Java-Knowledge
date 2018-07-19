package ca.mcmaster.spring.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 19, 2018 2:29:00 PM
 * @version 1.0
 */
public class ProxyTest {
	public static void main(String[] args) throws Exception {
		final ProxyService service = new ProxyServiceImpl();
		ProxyService proxy = (ProxyService) Proxy.newProxyInstance(service.getClass().getClassLoader(), 
				new Class[]{ProxyService.class}, new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						Monitor.begin();
						Object ret = method.invoke(service, null);
						Monitor.end();
						return ret;
					}
				});
		proxy.doOperation();
		System.out.println(proxy);
	}
}

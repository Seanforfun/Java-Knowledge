# AOP

### AOP术语
![AOP术语](https://i.imgur.com/6MTNibM.png)
1.通知Advice（方法级别的增强）:
* 想要添加的功能。对已有的方法想要添加的功能。
* 说明了干什么什么时候干。
* 例如在事件之前添加记录日志的代码就是一个Advice。

2.连接点JoinPoint:
* 允许添加通知的位置，几乎所有的方法都可以添加通知。
* 要对什么方法进行增强?所有的连接点都可以被增强，所以几乎所有的方法都是连接点。

3.切入点Pointcut:
* 具体对那些方法进行增强，那些需要被增强的Joinpoint被称为切点。
* 一个类有100个Jointpoint，而我们只想要对一个方法进行增强，就需要把那个方法定义为切点。

4.切面Aspect:
* Advice和Pointcut的结合。个人认为这是一个抽象概念。
* 通知说明了干什么和什么时候干（什么时候通过方法名中的befor，after，around等就能知道），二切入点说明了在哪干（指定到底是哪个方法），这就是一个完整的切面定义。

5.引入Introduction（类级别的增强）:
* 类似于Advice，但是是针对类级别的增强，给某个类添加方法或属性。
* 例如某个类没有实现某个接口，我们可以通过Introduce添加接口实现的逻辑。

6.目标Target：
* 引入中所提到的目标类。

7.织入Weaving:
* 把切面应用到目标对象来创建新的代理对象的过程。
* 织入的过程被分为三种：
	* 编译期的织入， 要求使用特殊的Java编译器。
	* 类装载期的织入，要求使用特殊的ApplicationClassLoader。
	* 动态代理的织入，在运行期使用动态代理对方法进行增强。
* Spring采用动态代理织入，而AspectJ通过编译期织入和类装载期织入。

### 代理
* JDK的动态代理
	* 要生成代理的对象必须要实现某个接口。

```Java
public static void main(String[] args) throws Exception {
		final ProxyService service = new ProxyServiceImpl();	//实现的就是当前实例的代理。
		ProxyService proxy = (ProxyService) Proxy.newProxyInstance(service.getClass().getClassLoader(),
				new Class[]{ProxyService.class}, new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						Monitor.begin();	//在通过反射调用方法之前实行织入。
						Object ret = method.invoke(service, null);
						Monitor.end();
						return ret;
					}
				});
		proxy.doOperation();
		System.out.println(proxy);
	}
```

* CgLib的动态代理
	* 为类创建一个子类，并在子类方法中拦截父类父类方法的调用并织入。

```Java
public class CglibProxy implements MethodInterceptor {
	private Enhancer enhancer = new Enhancer();	//这是CgLib的增强器对象。
	private Object getProxy(Class clazz){
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
	}
	@Override
	// 拦截方法，在代理调用父类的方法之前和之后进行织入。
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
```

* Spring的ProxyFactory类
```Java

```

### 增强类
#### 前置增强
1. 通过继承MethodBeforeAdvice实现before方法，并在before中实现要织入的前置增强。
```Java
// 定义前置增强的方法
public void before(Method arg0, Object[] args, Object arg2) throws Throwable {
	String name = (String) args[0];
	System.out.println("Hello Mr/Ms. " + name);
}
	@Test
	public void test() {
		Waiter waiter = new NaiveWaiter();
		BeforeAdvice greetBeforeAdvice = new GreetBeforeAdvice();
		ProxyFactory pf = new ProxyFactory(waiter);	//通过Spring代理工厂的方法生成代理对象。
		pf.addAdvice(greetBeforeAdvice);	//织入前置增强。
		Waiter proxy = (Waiter) pf.getProxy();
		proxy.greetTo("Sean");
	}
```

#### 通过xml配置生成代理，织入前置增强
```xml
<!-- 通过ProxyFactoryBean配置代理 -->
<!-- 定义了前置增强的方法 -->
<bean id="greetingAdvice" class="ca.mcmaster.spring.aop.GreetBeforeAdvice"/>
<!-- 要生成代理的对象 -->
<bean id="target" class="ca.mcmaster.spring.aop.NaiveWaiter"/>
<!-- 生成的代理对象 -->
<bean id="waiter" class="org.springframework.aop.framework.ProxyFactoryBean" scope="singleton">
	<property name="proxyInterfaces" value="ca.mcmaster.spring.aop.Waiter"/>
	<!-- 要织入的增强 -->
	<property name="interceptorNames" value="greetingAdvice"/>
	<property name="target" ref="target"/>
	<!-- 如果设置为true，则强制使用CgLib， 单例适合CgLin，JDK适合其他-->
	<!-- Cglib创建慢，效率高， JDK Proxy创建快，效率低-->
	<property name="optimize" value="true"/>
</bean>
```

#### 后置增强
1. 通过实现AfterReturningAdvice接口实现后置增强。xml配置和前置增强类似。
```Java
public class GreetingAfterAdvice implements AfterReturningAdvice {
	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		String name = (String) args[0];
		System.out.println("Goodbye " + name);
	}
}
```

#### 环绕增强
1. 通过实现MethodInterceptor接口并实现invoke方法。
```Java
public class GreetingInterceptor implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] arguments = invocation.getArguments();
		String name = (String) arguments[0];
		System.out.println("Hi! " + name);	//环绕前增强
		invocation.proceed();	//对实际的方法调用。
		System.out.println("Goodbye! " + name);	//环绕后增强
		return null;
	}
}
```

#### 异常抛出增强
1. 用于增强抛出异常后的情况，可以用于事务的回滚。
2. 需要实现ThrowsAdvice。
```Java
public class GreetAfterException implements ThrowsAdvice {
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable{
		System.out.println("Get exception: " + ex.getMessage());
		System.out.println("Method: " + method.getName());
	}
}
```




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

### 增强类
* 增强的xml配置
```xml
<!-- 通过ProxyFactoryBean配置代理 -->
<!-- 定义了前置增强的方法 -->
<bean id="greetingAdvice" class="ca.mcmaster.spring.aop.GreetBeforeAdvice"/>
<!-- 定义后置增强 -->
<bean id="greetingAfter" class="ca.mcmaster.spring.aop.GreetingAfterAdvice"/>
<!-- 定义环绕增强 -->
<bean id="greetingRound" class="ca.mcmaster.spring.aop.GreetingInterceptor"/>
<!-- 定义异常后增强 -->
<bean id="greetingException" class="ca.mcmaster.spring.aop.GreetAfterException"/>
<!-- 要生成代理的对象 -->
<bean id="target" class="ca.mcmaster.spring.aop.NaiveWaiter"/>
<!-- 生成的代理对象 -->
<bean id="waiter" class="org.springframework.aop.framework.ProxyFactoryBean" scope="singleton">
	<property name="proxyInterfaces" value="ca.mcmaster.spring.aop.Waiter"/>
	<!-- 要织入的增强 -->
	<property name="interceptorNames" value="greetingAdvice, greetingAfter, greetingRound, greetingException"/>
	<!-- 要为哪个类生成代理 -->
	<property name="target" ref="target"/>
	<!-- CGlib的运行效率是JDK proxy的10倍，但是JDK proxy的生成速度是CGLib的8倍，所以对于
	单例模式，我们希望使用CGLIB，因为在加载容器的时候我们就会生成所有的单例（除非懒加载），
	但是对于原型模式的对象，每次需要均会生成一个新对象，此时我们倾向于使用JDK Proxy。 -->
	<!-- 当optimize设置为true时，就会强制使用CGLIB。 -->
	<property name="optimize" value="true"/>
	<!-- 选择是否使用对类进行代理(而不是对接口进行代理)，所以当这个选项设置为true时，
	强制使用CGLIB。 -->
	<property name="proxyTargetClass" value="true"/>
	<!-- 默认使用单例模式 -->
	<property name="singleton" value="true"/>
</bean>
```

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

### 创建切面
* 切面是Advise和PointCut的集合，在定义了切面后就知道增强是什么，要在哪个方法的那个位置进行增强。

* Spring的切点类
```Java
public interface Pointcut {
	ClassFilter getClassFilter();	//确定当前的类是否匹配过滤条件。
	MethodMatcher getMethodMatcher();	//当前的方法是否匹配过滤条件。
	Pointcut TRUE = TruePointcut.INSTANCE;
}
```

#### 切点的类型
1. 静态方法切点：org.springframework.aop.support.StaticMethodMatcherPointcut
2. 动态方法切点：org.springframework.aop.support.DynamicMethodMatcherPointcut
3. 注解切点：org.springframework.aop.support.annotation.AnnotationMatchingPointcut
4. 表达式切点：org.springframework.aop.support.ExpressionPointcut
5. 流程切点：org.springframework.aop.support.ControlFlowPointcut
6. 复合切点：org.springframework.aop.support.ComposablePointcut

#### 切面类型
1. Advisor: 一般的切面，太过宽泛，不会直接使用。
2. PointcutAdvisor: 具有切点的切面，包含Advise和Pointcut两个类，经常使用。
3. IntroductionAdvisor:引介切面。

#### 切面的xml配置，我个人觉得很繁琐，但是研究有助于对aop的理解
```xml
<!-- 配置切面 -->
<!-- seller和waiter的实例对象，将会为这两个对象生成代理 -->
<bean id="waiterTarget" class="ca.mcmaster.spring.aop.wiring.Waiter" scope="singleton"/>
<bean id="sellerTarget" class="ca.mcmaster.spring.aop.wiring.Seller" scope="singleton"/>
<!-- 配置通知(增强的内容) -->
<bean id="greetingBeforeAdvice" class="ca.mcmaster.spring.aop.wiring.GreetingBeforeAdvice" scope="singleton"/>
<!-- 配置切面(内部定义增强的内容和切点) -->
<!-- 其中对方法和类进行了匹配 -->
<bean id="greetingAdvisor" class="ca.mcmaster.spring.aop.wiring.GreetingAdvisor" scope="singleton">
	<!-- 将通知织入切面 -->
	<property name="advice" ref="greetingBeforeAdvice"/>
</bean>
<!-- 将切面织入代理对象 -->
<!-- 定义了要生成的多个Bean对象代理的父类 -->
<bean id="parent" class="org.springframework.aop.framework.ProxyFactoryBean" abstract="true">
	<property name="interceptorNames" value="greetingAdvisor"/>
	<property name="proxyTargetClass" value="true"/>
</bean>
<bean id="waiterProxy" parent="parent">
	<property name="target" ref="waiterTarget"/>
</bean>
<bean id="sellerProxy" parent="parent">
	<property name="target" ref="sellerTarget"/>
</bean>
```

#### 切面的增强
1. 前置增强
```Java
public class GreetingBeforeAdvice implements MethodBeforeAdvice {
	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		System.out.println("Wow! Hello " + args[0] + "!");
	}
}
```

2. 定义切面
```Java
public class GreetingAdvisor extends StaticMethodMatcherPointcutAdvisor {
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		return "greetTo".equals(method.getName());	//方法匹配
	}
	@Override
	public ClassFilter getClassFilter() {
//		return super.getClassFilter();
		return new ClassFilter() {
			@Override
			public boolean matches(Class<?> clazz) {
				// 要匹配的类是不是clazz的子类。
				return Waiter.class.isAssignableFrom(clazz);	//类匹配
			}
		};
	}
}
```

3. 实现注入，进行测试
```Java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:beans.xml"})
public class GreetingAdvisorTest {
	@Autowired
	@Qualifier("waiterProxy")
	private Waiter waiterProxy;
	@Autowired
	@Qualifier("sellerProxy")
	private Seller sellerProxy;
	@Test
	public void test() {
		System.out.println(waiterProxy);
		waiterProxy.greetTo("Sean");
		sellerProxy.greetTo("Seanforfun");
	}
}
```

#### 通过正则表达式匹配切面
```xml
<!-- 通过正则表达式对方法和类进行匹配 -->
<bean id="regexAdvisor" class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
	<property name="advice" ref="greetingBeforeAdvice"/>
	<property name="patterns">
		<list>
			<!-- 通过正则匹配所有包含greet的方法 -->
			<value>.*greet.*</value>
		</list>
	</property>
</bean>
```

#### 动态切面
1. 动态切面的动态体现在切点上，可以针对参数对方法进行增强。
```Java
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
		return names.contains((String)args[0]);	//检查参数是否符合要求
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
```

2. 在xml中进行配置，实际上切面由pointcut和增强方法构成，所以我们要通过定义DefaultBeanFactoryPointcutAdvisor中的这两个选项来定义切面。
```xml
<!-- 通过动态匹配对传入的参数进行动态匹配 -->
<bean id="dynamicAdvisor" class="org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor">
	<!-- 通过匿名内部类定义切点实例 -->
	<property name="pointcut">
		<bean class="ca.mcmaster.spring.aop.wiring.GreetingDynamicPointcut"/>
	</property>
	<!-- 通过匿名内部类定义前置增强的实例 -->
	<property name="advice" ref="greetingBeforeAdvice"/>
</bean>
```

3. 测试
```Java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:beans.xml"})
public class GreetingDynamicPointcutTest {
	@Autowired
	@Qualifier("waiterProxy")
	private Waiter waiter;
	@Test
	public void test() {
		waiter.greetTo("Irene");	//结果只会对注册了的名字进行增强。
		waiter.greetTo("X.B");
	}
}
```

#### 流程切面（慢！）
> 流程切面多用于策略模式和门面模式的结合

```Java
public class WaiterDelegant {
	private Waiter waiter;
	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}
	public void service(String clientName){
		waiter.greetTo(clientName);	//我们只想对其中的某一个方法进行增强
		waiter.serveTo(clientName);
	}
}
```

1. 通过xml进行流程切面
```xml
<!-- 通过设置流程切面 -->
<!-- 针对某个类的对于某个方法的拦截 -->
<bean id="controlFlowPointcut" class="org.springframework.aop.support.ControlFlowPointcut">
	<constructor-arg name="clazz" value="ca.mcmaster.spring.aop.wiring.WaiterDelegant"/>
	<constructor-arg name="methodName" value="greetTo"/>
</bean>
<!-- 配置切面，将切点和增强注入 -->
<bean id="controlFlowAdvisor" class="org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor">
	<property name="pointcut" ref="controlFlowPointcut"/>
	<property name="advice" ref="greetingBeforeAdvice"/>
</bean>
```

### 复合切面
> 复合切面用于合并多种切点

* 此段代码并没有完全成功，只是实现了流程控制并没有实现name match。
```Java
public class GreetingComposablePointcut {
	public Pointcut getIntersectionPointcut(){
		ComposablePointcut cp = new ComposablePointcut();
		Pointcut pt1 = new ControlFlowPointcut(WaiterDelegant.class, "service");
		NameMatchMethodPointcut pt2 = new NameMatchMethodPointcut();
		pt2.addMethodName("greetTo");
		cp.intersection((Pointcut)pt2).intersection(pt1);
		return cp;
	}
}
```
```xml
<!-- 设置复合切面 -->
<bean id="composablePointcut" class="ca.mcmaster.spring.aop.wiring.GreetingComposablePointcut"/>
<bean id="composableAdvisor" class="org.springframework.aop.support.DefaultPointcutAdvisor">
	<property name="pointcut" value="#{composablePointcut.intersectionPointcut}"/>
	<property name="advice" ref="greetingBeforeAdvice"/>
</bean>
```

### 自动创建代理
> 当我们要进行织入的时候，我们需要使用代理。上面的代码中，所生成的代理对象都是通过ProxyFactoryBean进行生成的，较为麻烦。我们可以通过使用自动代理的机制，让容器为我们自动生成代理对象。

#### BeanPostProcessor
![BeanPostProcessor](https://i.imgur.com/It3wsSB.png)

#### BeanNameAutoProxyCreator
> 基于Bean配置名的自动代理创建器。(命名规则)

```xml
<!-- 使用bean名称进行自动代理 -->
<bean class="org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator" >
	<property name="beanNames">
		<list>
			<!-- 通过正则表达式进行bean对象的匹配 -->
			<value>*Target</value>
		</list>
	</property>
	<property name="interceptorNames" value="greetingBeforeAdvice"/>
	<property name="optimize" value="true"/>
</bean>
```

* 测试
```Java
public class AutomaticBeanFactoryTest {
	@Autowired
	@Qualifier("waiterTarget")
	private Waiter waiter;
	@Autowired
	@Qualifier("sellerTarget")
	private Seller seller;
	@Test
	public void test(){
		waiter.greetTo("Seanforfun");
		seller.greetTo("Sean");
	}
}
Wow! Hello Seanforfun!
Waiter Greet to Seanforfun
Wow! Hello Sean!
Seller greet to Sean
```

#### DefaultAdvisorAutoProxyCreator
> 切面(advisor)的信息中包含了要增强的对象(pointcut)和增强的方法(advice),其中提供了足够的信息用于生成代理对象。所以当我们定义了DefaultAdvisorAutoProxyCreator对象后，可以为要增强的bean对象自动生成代理。

```xml
<!-- 通过切面生成代理 -->
<!-- 切面本身包括了增强方法和要增强的对象，这两个对象已经包括了足够的信息，
当匹配到了要被增强的对象时，会自动生成代理。 -->
<bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"/>
```

* 所有作为bean对象生成的切面均被注册
```Java
对greetTo进行静态检查
对greetTo进行静态检查
对serveTo进行静态检查
对toString进行静态检查
对clone进行静态检查
对greetTo进行静态检查
Wow! Hello Seanforfun!
Wow! Hello Seanforfun!
对greetTo进行动态检查
Waiter Greet to Seanforfun
Wow! Hello Sean!
Seller greet to Sean
```
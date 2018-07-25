# AspectJ in Spring

### Annotation in Java
> My previous conclusion about annotation:
> [Annotation V2.0](https://github.com/Seanforfun/JavaCore/blob/master/Conclusions/Annotation.md)

#### 注解中域的数据类型
1. 字符串
2. 布尔型
3. 枚举
4. 注解
5. 上述各种类型的数组形式

* 定义一个注解
```Java
public @interface Review {
	Grade value();
	String reviewer();
}
```

* 注解域以及其反射调用
```Java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Reviews {
	Review[] value();	//虽然被叫做域可仍然是一个方法，在反射中通过方法调用。
}
```

* 反射中调用注解并读取信息
```Java
@Reviews({@Review(value=Grade.EXCELLENT, reviewer="Seanforfun"), 
		@Review(value=Grade.UNSATISFACTORY, reviewer="Sean")})
	public static void annotationFieldTest() throws Exception{
		Method testMethod = AnnotationTest.class.getDeclaredMethod("annotationFieldTest", null);
		Annotation[] annotations = testMethod.getDeclaredAnnotations();
		for(Annotation annotation : annotations){
			Method method = annotation.getClass().getDeclaredMethod("value", null);
			Review[] reviews = (Review[]) method.invoke(annotation, null);
			for(Review r : reviews){
				System.out.println(r);
			}
		}
	}
在main函数中调用获得的结果：
@ca.mcmaster.spring.aop.annotation.Review(value=EXCELLENT, reviewer=Seanforfun)
@ca.mcmaster.spring.aop.annotation.Review(value=UNSATISFACTORY, reviewer=Sean)
```

### AspectJ
#### AspectJ定义切面
```Java
@Aspect		//	Define this class as an aspect
public class PreGreetingAspect {
	// 定义了切点
	@Before("execution(* ca.mcmaster.spring.aop..*.greetTo(..))")
	public void beforeGreeting(){	//定义了增强的内容
		System.out.println("How do you do?");
	}
}
@Test
	public void test() throws Exception {
		NaiveWaiter waiter = new NaiveWaiter();
		AspectJProxyFactory factory = new AspectJProxyFactory();	//AspectJ的代理工厂。
		factory.setTarget(waiter);	//设置要代理的对象
		factory.addAspect(PreGreetingAspect.class);	//向对象中织入切面，通过反射机制获得切点和增强。
		Waiter proxy = factory.getProxy();	//获得一个被增强的对象
		proxy.greetTo("Sean");
		proxy.serve("Sean");
	}
How do you do?
Greet to Sean
```

#### 通过xml配置使用AspectJ
```xml
<!-- 配置AspectJ -->
<!-- 配置AspectJ切面bean -->
<bean class="ca.mcmaster.spring.aop.wiring.PreGreetingAspect"/>
<!-- 该bean会自动扫描@AspectJ注解, 同时也会自动织入所有通过xml配置的切面 -->
<bean class="org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator"/>
```

#### 通过schema进行自动代理装配
```xml
<!--只会扫描属性上的注解-->
<context:annotation-config></context:annotation-config>
```

#### AspectJ表达式
1. execution(): 表达式主体。
2. 第一个*号：表示返回类型，*号表示所有的类型。
3. 包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.sample.service.impl包、子孙包下所有类的方法。
4. 第二个*号：表示类名，*号表示所有的类。
5. *(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
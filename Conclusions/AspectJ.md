# AspectJ
1. AspectJ是一个代码生成工具（Code Generator）。
2. AspectJ语法就是用来定义代码生成规则的语法。您如果使用过Java Compiler Compiler (JavaCC)，您会发现，两者的代码生成规则的理念惊人相似。
3. AspectJ有自己的语法编译工具，编译的结果是Java Class文件，运行的时候，classpath需要包含AspectJ的一个jar文件（Runtime lib）。

### AspectJ Realization
1. AspectJ从文件列表中取出所有的文件，读出这些文件名进行分析。
2. AspectJ从这些文件中发现了Aspect的定义，读取出aspect的代码生成规则。
3. AspectJ编译器根据第二部中获得的规则，修改了源代码。（说明AspectJ是一个代码生成器）
	* AspectJ编译器从文件中发现了切点（pointcut）
	* AspectJ又发现了advice，于是将会对切点进行织入
4. AspectJ生成新的代码，生成的代码则是按照规则对方法进行增强。

### AspectJ Annotation
1. 要想把一个类变成切面类，需要两步:
	* 在类上使用 @Component 注解 把切面类加入到IOC容器中.(这样Spring才会生成该类对象)
	* 在类上使用 @Aspect 注解 使之成为切面类（定义一个切面）
2. 在SpringBoot中使用AspectJ的方法：
```Java
@Controller
@AspectJ
public class AdminAspect {
	@Pointcut("execution(public * ca.seanforfun.blog.controller.AdminController.*(..))")
	public void AdminOperations(){};	//通过execution语句，定义了一系列的方法作为切入点。

	@Before("AdminOperations()")	//针对方法编写Advice，此处编写的前置增强的方法。
	public void checkLogin(JoinPoint jp) throws IOException, ServletException, UserNotLoginException{
		System.out.println("=======================================");
		System.out.println("=======================================");
		System.out.println("=======================================");
		System.out.println("=======================================");
		System.out.println("=======================================");
		//获取Session，检查用户是否登录
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpSession session = attributes.getRequest().getSession();
		if(null == session.getAttribute("loginUser")){
			throw new UserNotLoginException();
		}
	}
}
```

### Reference
1. [AspectJ框架实现原理](https://blog.csdn.net/zhao9tian/article/details/37762389)

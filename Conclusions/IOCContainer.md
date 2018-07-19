# IOC Container

### IOC容器的初始化
![IOC容器的初始化](https://i.imgur.com/shMORhc.jpg)
```Java
public void refresh() throws BeansException, IllegalStateException {
	synchronized (this.startupShutdownMonitor) {
		// Prepare this context for refreshing.
		prepareRefresh();
		// Tell the subclass to refresh the internal bean factory.
		// 初始化BeanFactory
		ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
		// Prepare the bean factory for use in this context.
		prepareBeanFactory(beanFactory);
		try {
			// Allows post-processing of the bean factory in context subclasses.
			postProcessBeanFactory(beanFactory);
			// Invoke factory processors registered as beans in the context.
			//调用工厂后处理器
			invokeBeanFactoryPostProcessors(beanFactory);
			// Register bean processors that intercept bean creation.
			//注册Bean后处理器
			registerBeanPostProcessors(beanFactory);
			// 初始化消息源
			initMessageSource();
			// 初始化应用上下文时间广播器
			initApplicationEventMulticaster();
			// Initialize other special beans in specific context subclasses.
			// 初始化其他特殊的Bean, 具体由子类实现。
			onRefresh();
			// 注册监听器事件
			registerListeners();
			// 初始化所有的bean（不包括懒加载）
			finishBeanFactoryInitialization(beanFactory);
			// 完成刷新并发布容器刷新事件
			finishRefresh();
		}
		catch (BeansException ex) {
			//...
		}
	}
}
```

#### BeanDefinition
> BeanDefinition是一种对xml的java代码解释，和xml中的一个<bean>对应，在<bean>标签中的属性和BeanDefinition中的属性相对应。

1. 通过org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(RootBeanDefinition, String, BeanFactory)方法生成对象的实例
```Java
public Object instantiate(RootBeanDefinition bd, String beanName, BeanFactory owner) {
	// Don't override the class with CGLIB if no overrides.
	if (bd.getMethodOverrides().isEmpty()) {
		Constructor<?> constructorToUse;
		synchronized (bd.constructorArgumentLock) {
			constructorToUse = (Constructor<?>) bd.resolvedConstructorOrFactoryMethod;	//获取构造器或工厂方法。
			if (constructorToUse == null) {
				final Class<?> clazz = bd.getBeanClass();	//获得类对象
				if (clazz.isInterface()) {
					throw new BeanInstantiationException(clazz, "Specified class is an interface");	//如果当前的类是一个接口则无法被实例化。
				}
				try {
					//检查SecurityManager以获取可以使用的构造器方法。
					if (System.getSecurityManager() != null) {
						constructorToUse = AccessController.doPrivileged(new PrivilegedExceptionAction<Constructor<?>>() {
							@Override
							public Constructor<?> run() throws Exception {
								return clazz.getDeclaredConstructor((Class[]) null);
							}
						});
					}
					else {
						constructorToUse = clazz.getDeclaredConstructor((Class[]) null);
					}
					bd.resolvedConstructorOrFactoryMethod = constructorToUse;
				}
				catch (Exception ex) {
					throw new BeanInstantiationException(clazz, "No default constructor found", ex);
				}
			}
		}
		//根据构造器获取一个对象实例。
		return BeanUtils.instantiateClass(constructorToUse);
	}
	else {
		// Must generate CGLIB subclass.
		return instantiateWithMethodInjection(bd, beanName, owner);
	}
}
```

#### BeanWrapper
1. Bean包裹器
2. 属性访问器
3. 属性编辑器注册表

#### 使用properties文件
1. 在xml引入context命名空间，并定义properties文件的路径
```xml
<!-- 通过context域引入properties文件 -->
<context:property-placeholder location="classpath:jdbc.properties"/>
<!-- 通过PropertyPlaceholderConfigurer引入properties文件 -->
<bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer" scope="singleton">
	<property name="location" value="classpath:jdbc.properties"/>
</bean>
```

2. 将properties文件存储的内容进行注入
```xml
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
	<property name="driverClassName" value="${driverClassName}"/>
	<property name="url" value="${url}"/>
	<property name="username" value="${username}"/>
	<property name="password" value="${password}"/>
</bean>
```

3. 通过注解引入properties文件
* 原始类
```Java
@Component("myDataSource")
public class MyDataSource {
	@Value("${driverClassName}")	//通过改种方法将属性的值进行值的注入。
	private String driverClassName;
	@Value("${url}")
	private String url;
	@Value("${username}")
	private String username;
	@Value("${password}")
	private String password;
	public String getDriverClassName() {
		return driverClassName;
	}
	public String getUrl() {
		return url;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
```

4. Properties文件自身的引用
* properties文件可以将内容分开存储相互引用。
```Properties
dbName=sampledb
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/${dbName} #此处的dbName引用了别处定义的值。
```

### 引用Bean的属性值
> 有的配置信息希望放在bean内部由代码动态控制，所以我们有时候希望引用新的bean的值动态调整系统的信息。

* 通过xml获得bean对象的传入值
```xml
<bean id="dataSourceConfig" class="ca.mcmaster.spring.di.DataSourceConfig">
	<!-- 通过已经定义的bean对象获得传入的值 -->
    <property name="maxTimeOut" value="#{sysConfig.maxTimeOut}"/>
    <property name="connections" value="#{sysConfig.connections}"/>
</bean>
```

* 通过注解获得bean对象传入的值
```Java
@Component("dataSourceConfig")
public class DataSourceConfig {
	@Value("#{sysConfig.maxTimeOut}")	//和xml式的注入很类似。
	private Integer maxTimeOut;
	@Value("#{sysConfig.connections}")
	private Integer connections;
	public Integer getMaxTimeOut() {
		return maxTimeOut;
	}
	public Integer getConnections() {
		return connections;
	}
}
```

### 国际化
1. 传统的国际化，通过NumberFormat实现单位的国际化。
```Java
Locale ch = new Locale("zh", "CN");
NumberFormat zhnf = NumberFormat.getCurrencyInstance(ch);
Locale ca = new Locale("en", "CA");
NumberFormat canf = NumberFormat.getCurrencyInstance(ca);
double currency = 12345.6D;
System.out.println(zhnf.format(currency)); //￥12,345.60
System.out.println(canf.format(currency)); //$12,345.60
```

2. 对于Message进行国际化装配
```Java
// 信息格式化串
String chMsg = "{0}, 你好！你于{1}在工商银行存入{2}元！";
String usMsg = "At {1, time, short} On {1, date,long}, {0} saved {2, number, currency}";
// 定义动态占位符的参数
Object[] params = {"Sean", new GregorianCalendar().getTime(), 1000000000};
// 指定国际化信息
MessageFormat zhMf = new MessageFormat(chMsg, Locale.CHINESE);
//装配参数
String zhPattern = zhMf.format(params);
System.out.println(zhPattern);	//Sean, 你好！你于18-7-19 上午9:59在工商银行存入1,000,000,000元！
MessageFormat usMf = new MessageFormat(usMsg, Locale.CANADA);
String usPattern = usMf.format(params);
System.out.println(usPattern);	//At 9:59 AM On July 19, 2018, Sean saved $1,000,000,000.00
```

3. 通过ResourceBundle实现国际化
* 在项目中定义国际化的properties文件，该文件的文件名有一定的规范：<资源名>_<语言名>.properties， 例如greeting_en_CA.properties和greeting_zh_CN.properties。

* 在代码中调用
```Java
ResourceBundle enBundle = ResourceBundle.getBundle("ca/mcmaster/spring/i18n/greeting", Locale.CANADA);	//前一个参数写出了全路径名（最后一个位置为资源名），第二个参数是国际化的信息。
ResourceBundle cnBundle = ResourceBundle.getBundle("ca/mcmaster/spring/i18n/greeting", Locale.CHINESE);
System.out.println(enBundle.getString("greeting.common"));
System.out.println(new String(cnBundle.getString("greeting.common").getBytes("ISO-8859-1"), "utf-8"));	//properties文件是通过ISO-8859-1编码集编码的，我们要转到utf-8编码集。
Hello!
ä½ å¥½ï¼	//未转编码集的输出结果。
你好！
```

4. 资源文件中使用格式化串
* 在资源文件中定义语句串
```
greeting.common=Hello {0}! Today is {1}!
```
* 在Java文件中获取语句串并赋值
```Java
ResourceBundle fmtBundle = ResourceBundle.getBundle("ca/mcmaster/spring/i18n/fmt_greeting", Locale.CANADA);
MessageFormat msgFormat = new MessageFormat(fmtBundle.getString("greeting.common"), Locale.CANADA);
Object[] params = {"Sean", new GregorianCalendar().getTime()};
String formatMsg = msgFormat.format(params);
System.out.println(formatMsg);
Hello Sean! Today is 19/07/18 10:34 AM!
```

5. 利用Spring来获取ResourceBundle信息
```xml
<bean id="myResource" class="org.springframework.context.support.ResourceBundleMessageSource">
	<property name="basenames">
		<list>
    		<value>ca/mcmaster/spring/i18n/fmt_greeting</value>
    	</list>
    </property>
</bean>
```

* 可以通过ReloadableResourceBundleMessageSource来设置cacheSeconds使框架会刷新resource文件。
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
<!-- 引入properties文件 -->
<context:property-placeholder location="classpath:jdbc.properties"/>
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
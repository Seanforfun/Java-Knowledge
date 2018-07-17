# Realization of IoC
> 面向对象的处理中，对象封装了数据和堆数据的处理，对象的依赖关系常常体现在对数据和方法的依赖上。所以我们可以将对象之间的依赖交给IoC容器去处理，减少了对象之间的耦合。

### What is Inversed?
1. 在我们原来生成一个对象的实例时，是我们主动将对象的依赖进行注入，例如我们会生成新的实例并赋值给引用，这是正向的控制。
2. 反转控制是指，现在我们生成一个对象的实例，由IoC容器将依赖进行被动注入（注意，此时注入依赖的行为已经不是主动地了，而是需要什么就会被IoC容器注入什么），从而实现了控制的反转。
3. 反转指的是责任的反转，原本依赖由对象主动生成转换成被IoC容器注入。

### 注入的方法
1. 构造函数注入：在构造函数中将属性传入对象。
2. 属性注入：通过get和set方法注入。
3. 接口注入：将调用类的所有依赖抽取到一个接口中，通过实现该接口实现方法的注入。
4. 通过反射注入：
```Java
public class Car {	//此处的Car是一个POJO
	private String color;
	private String brand;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
}
public class ReflectTest {
	public static Car getCarInstance() throws ClassNotFoundException, Exception, SecurityException{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();	//此处获取的是ApplicationClassLoader.
		Class<?> carClass = loader.loadClass("ca.mcmaster.spring.reflect.Car");
		Constructor<?> constructor = carClass.getConstructor(null);	//获取构造器，并通过构造器创建一个类的对象。
		Car car = (Car) constructor.newInstance();
		Method setColorMethod = carClass.getDeclaredMethod("setColor", String.class);	//通过反射获取方法，通过方法将属性进行注入。
		setColorMethod.invoke(car, "Grey");
		Method setBrandMethod = carClass.getDeclaredMethod("setBrand", String.class);
		setBrandMethod.invoke(car, "RAV4");
		return car;
	}
	public static void main(String[] args) throws ClassNotFoundException, SecurityException, Exception {
		Car car = ReflectTest.getCarInstance();
		System.out.println("Color: " + car.getColor());
		System.out.println("Brand: " + car.getBrand());
	}
}
```

### ClassLoader
```Java
public class ClassloaderTest {
	public static void main(String[] args) {
		ClassLoader applicationClassLoader = Thread.currentThread().getContextClassLoader();
		System.out.println("Current classloader: " + applicationClassLoader);
		System.out.println("Parent classloader: " + applicationClassLoader.getParent());
		System.out.println("Boot classloader: " + applicationClassLoader.getParent().getParent());
	}
}
Current classloader: sun.misc.Launcher$AppClassLoader@73d16e93
Parent classloader: sun.misc.Launcher$ExtClassLoader@15db9742
Boot classloader: null //启动加载器是通过C++实现的，用于加载${JAVA_HOME}/lib中的类。
```

### Resource对象
> Resource对象是对资源文件的一种抽象，提供了多种对于文件信息获取的方法，可以用于Spring定位包含BeanDefinition的文件并获取资源。

* 两种通过输入流操作获取Resource内容的方法。
```Java
public class ResourceTest {
	public static void main(String[] args) throws IOException {
		InputStream is = null;
		try{
			Resource resource = new FileSystemResource("F://JavaEE_Project/JavaCore/src/beans.xml");
			System.out.println(resource.getFilename());
			System.out.println(resource.getDescription());
			is = resource.getInputStream();
			byte[] b = new byte[1024];
			int index = 0;
			StringBuilder sb = new StringBuilder();
			while((index = is.read(b)) != -1){	//通过输入流的read方法一致读到EOF
				String s = new String(b);
				sb.append(s);
				index = 0;
			}
			System.out.println(sb.toString());
			System.out.println("=====================");
			EncodedResource encRes = new EncodedResource(resource, "UTF-8");
			String content = FileCopyUtils.copyToString(encRes.getReader());	//通过Spring封装好的FileCopyUtils工具类，实现从Reader中获取字符并转换成字符串。
			System.out.println(content);
		}finally{
			is.close();
		}
	}
}
```

### BeanFactory and Application Context
* BeanFactory是Spring的框架设施，就是我们平时所说的IOC容器，是一种较为底层的容器，一般程序员并不会用到，却是Spring实现的核心。
* ApplicationContext叫做应用上下文，面向Spring框架的开发者，有时我们也成为Spring容器。

#### BeanFactory
> BeanFactory是类的通用工厂，可以创建并管理各种POJO类，这些类要遵从一定的规范，这样Beanfaactory才能调用反射机制生成类对象。
*  The root interface for accessing a Spring bean container.
```Java
public interface BeanFactory {
	String FACTORY_BEAN_PREFIX = "&";
	Object getBean(String name) throws BeansException;
	<T> T getBean(String name, @Nullable Class<T> requiredType) throws BeansException;
	Object getBean(String name, Object... args) throws BeansException;
	<T> T getBean(Class<T> requiredType) throws BeansException;
	<T> T getBean(Class<T> requiredType, Object... args) throws BeansException;
	boolean containsBean(String name);	//当前容器是否含有包含某种名字的bean
	boolean isSingleton(String name) throws NoSuchBeanDefinitionException;	//当前指定的类是否是单例模式，可以在BeanDefinition中指定。
	boolean isPrototype(String name) throws NoSuchBeanDefinitionException;	//当前指定的对象是否是原型模式，可以在BeanDefinition中定义。
	boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException;	//确定当前指定的bean是否是指定的Class类。
	boolean isTypeMatch(String name, @Nullable Class<?> typeToMatch) throws NoSuchBeanDefinitionException;
	@Nullable
	Class<?> getType(String name) throws NoSuchBeanDefinitionException;	//获得指定对象属于哪个类。
	String[] getAliases(String name);	//获得指定对象的别名。
}
```

### 编程式的使用IoC容器
```Java
public class DefaultListableBeanFactoryTest {
	public static void main(String[] args) {
		ClassPathResource resource = new ClassPathResource("beans.xml");	//创建了IoC配置文件的抽象资源，这个资源中包含了BeanDefinition。
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();	//创建一个beanFactory，实际上是一个spring对象的工厂类，用于生成注册的bean对象
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(factory);	//创建一个BeanDefinition的reader对象。
		xmlReader.loadBeanDefinitions(resource);	//通过reader从xml中加载到beanDefinition。
		Customer customer = (Customer) factory.getBean("customer");
		System.out.println(customer.getName());
	}
}
```

### IoC容器的初始化
1. BeanDefinition的Resource定位。
* 通过ClassPathResource载入
```Java
ClassPathResource resource = new ClassPathResource("beans.xml");
```

* 通过FileSystemXmlApplicationContext载入
```Java
public class FileSystemXmlApplicationContext extends AbstractXmlApplicationContext {
	public FileSystemXmlApplicationContext() {
	}
	public FileSystemXmlApplicationContext(ApplicationContext parent) {
		super(parent);
	}
	//单个包含BeanDefinition的文件。
	public FileSystemXmlApplicationContext(String configLocation) throws BeansException {
		this(new String[] {configLocation}, true, null);
	}
	//多个包含BeanDefinition的文件。
	public FileSystemXmlApplicationContext(String... configLocations) throws BeansException {
		this(configLocations, true, null);
	}
	public FileSystemXmlApplicationContext(String[] configLocations, ApplicationContext parent) throws BeansException {
		this(configLocations, true, parent);
	}
	public FileSystemXmlApplicationContext(String[] configLocations, boolean refresh) throws BeansException {
		this(configLocations, refresh, null);
	}
	public FileSystemXmlApplicationContext(String[] configLocations, boolean refresh, ApplicationContext parent)
			throws BeansException {
		super(parent);
		setConfigLocations(configLocations);
		if (refresh) {
			refresh();	//启动了BeanDefinition的载入。
		}
	}
	@Override
	protected Resource getResourceByPath(String path) {
		if (path != null && path.startsWith("/")) {
			path = path.substring(1);
		}
		return new FileSystemResource(path);
	}
}
```

* 对IoC容器的初始化
```Java
	protected final void refreshBeanFactory() throws BeansException {
		if (hasBeanFactory()) {	//如果已经存在了一个容器，则将原来的容器关闭。
			destroyBeans();
			closeBeanFactory();
		}
		try {
			DefaultListableBeanFactory beanFactory = createBeanFactory();	//实际上此处持有BeanDefinition的容器就是DefaultListableBeanFactory。
			beanFactory.setSerializationId(getId());
			customizeBeanFactory(beanFactory);
			loadBeanDefinitions(beanFactory);	//载入BeanDefinition
			synchronized (this.beanFactoryMonitor) {
				this.beanFactory = beanFactory;
			}
		}
		catch (IOException ex) {
			throw new ApplicationContextException("I/O error parsing bean definition source for " + getDisplayName(), ex);
		}
	}
	//生成一个IoC容器
	protected DefaultListableBeanFactory createBeanFactory() {
		return new DefaultListableBeanFactory(getInternalParentBeanFactory());
	}
```
2. BeanDefinition的载入。
```Java
public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
			// Prepare this context for refreshing.
			prepareRefresh();	//抽象方法，子类实现的更新容器的方法。
			// Tell the subclass to refresh the internal bean factory.
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
			// Prepare the bean factory for use in this context.
			prepareBeanFactory(beanFactory);
			try {
				// Allows post-processing of the bean factory in context subclasses.
				postProcessBeanFactory(beanFactory);
				// Invoke factory processors registered as beans in the context.
				invokeBeanFactoryPostProcessors(beanFactory);
				// Register bean processors that intercept bean creation.
				registerBeanPostProcessors(beanFactory);
				// Initialize message source for this context.
				initMessageSource();
				// Initialize event multicaster for this context.
				initApplicationEventMulticaster();
				// Initialize other special beans in specific context subclasses.
				onRefresh();
				// Check for listener beans and register them.
				registerListeners();
				// Instantiate all remaining (non-lazy-init) singletons.
				finishBeanFactoryInitialization(beanFactory);
				// Last step: publish corresponding event.
				finishRefresh();
			}
			catch (BeansException ex) {
				logger.warn("Exception encountered during context initialization - cancelling refresh attempt", ex);
				// Destroy already created singletons to avoid dangling resources.
				destroyBeans();
				// Reset 'active' flag.
				cancelRefresh(ex);
				// Propagate exception to caller.
				throw ex;
			}
		}
	}
```
3. 向IoC容器注册BeanDefinition。
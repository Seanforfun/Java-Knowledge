# Realization of IoC
> 面向对象的处理中，对象封装了数据和堆数据的处理，对象的依赖关系常常体现在对数据和方法的依赖上。所以我们可以将对象之间的依赖交给IoC容器去处理，减少了对象之间的耦合。

### What is Inversed?
1. 在我们原来生成一个对象的实例时，是我们主动将对象的依赖进行注入，例如我们会生成新的实例并赋值给引用，这是正向的控制。
2. 反转控制是指，现在我们生成一个对象的实例，由IoC容器将依赖进行被动注入（注意，此时注入依赖的行为已经不是主动地了，而是需要什么就会被IoC容器注入什么），从而实现了控制的反转。
3. 反转指的是责任的反转，原本依赖由对象主动生成转换成被IoC容器注入。

### BeanFactory
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
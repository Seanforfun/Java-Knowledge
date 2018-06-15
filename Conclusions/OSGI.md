# OSGI
1. OSGI容器需要实现的服务集合。
2. OSGI容器和应用之间的通信机制。
3. OSGI容器被设计专门用来开发可分解为功能模块的复杂的Java应用。
4. 打个比方，在MVC的框架中，我们想要修改Dao的代码，我们可以热更新Dao而不需要重启服务器。

### OSGI的使用
> 通过Eclipse创建一个Plug-in Project, 例如使用Hello OSGI Bundle作为模板，我们可以得到如下的模板类：

```Java
public class Activator implements BundleActivator {
	public void start(BundleContext context) throws Exception {
		System.out.println("Hello World!!");	//当服务启动的时候会运行的类。
	}
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");	//当服务结束时运行的方法。
	}
}
```
1. Activator类需要一个公有的无参数构造函数。OSGI框架会通过类反射的方式来实例化一个Activator类。
2. 容器启动bundle过程中负责调用你的Activator类的start方法。bundle可以在此初始化资源比如说初始化数据库连接。start方法需要一个参数，BundleContext对象。这个对象允许bundles以取得OSGI容器相关信息的方式和框架交互。如果某一个bundle有异常抛出，容器将对该bundle标记为stopped并不将其纳入service列表。
3. 容器关闭的时候会调用你的Activator类方法stop(),你可以利用这个机会做一些清理的操作。

### 依赖管理
1. OSGI框架是实现SOA的绝佳土壤。通过它可以实现bundles暴露服务接口给其他bundles消费而不需要让细节暴露。消费bundles甚至可以完全不知道提供服务的bundles。凭着可以良好的隐藏具体实现的能力，OSGI当之无愧是SOA的一种较完美的实现方案。
2. OSGI中，提供服务的bundle在OSGI容器上将一个POJO注册成一个service。消费者bundle请求OSGI容器中基于某个特殊接口的注册service。一旦找到，消费者bundle就会绑定它，然后就可以调用service中的方法了。举个例子会更容易说明。

#### SOA(Service oriented architecture)
1. 创建service和其Impl类
* 提供Service的一个项目/HelloService，其中存在一个服务接口和一个对应该接口的实现类。
	* Service接口：service.HelloService
```Java
public interface HelloService {
	public String sayHello();
}
```
	* Service实现类：service.impl.HelloImpl
```Java
public class HelloImpl implements HelloService {
	@Override
	public String sayHello() {
		System.out.println("Inside HelloServiceImple.sayHello()");  
		return "Say Hello"; 
	}
}
```

2. 导出服务的接口
* 通过MANIFEST.MF导出service服务接口，并在要使用当前服务的包中导入当前接口。
* 此时接口的实现方法是在引入包的项目中是隐蔽的，无法通过直接调用实现类获取。

3. 在服务接口中注册服务
> 在helloservice.Activator中的start方法中注册服务与其实现类。
```Java
public class Activator implements BundleActivator {
	private ServiceRegistration<HelloService> helloServiceRegistration;
	public void start(BundleContext context) throws Exception {
		HelloService helloService = new HelloImpl();	//创建一个服务的对象。
		helloServiceRegistration = context.registerService(HelloService.class, helloService, null);	//注册服务接口
	}
	public void stop(BundleContext context) throws Exception {
		helloServiceRegistration.unregister();	//在当前activator停止时注销服务。
	}
}
```

4. 在一个服务中调用另一个服务
> 虽然对象的实现类没有被导入，无法进行直接访问，但是仍然可以被调用。
```Java
public class Activator implements BundleActivator {
	private ServiceReference<?> serviceReference;
	public void start(BundleContext context) throws Exception {
		System.out.println("Hello World!!");
		serviceReference = context.getServiceReference(HelloService.class.getName());	//会通过反射的方式进行注入。会返回所有注册服务的reference。
		HelloService service = context.getService(serviceReference);	//从服务中获取我们需要的服务
		System.out.println(service.sayHello());
	}
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
		context.ungetService(serviceReference);
	}
}
```

5. 结果
```
Hello World!!
Inside HelloServiceImple.sayHello()
Say Hello
```

### Reference
[OSGI入门实例讲解（一）](https://blog.csdn.net/cruise_h/article/details/27369749)

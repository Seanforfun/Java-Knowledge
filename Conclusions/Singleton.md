# Singleton 单例模式
* 单例模式保证了在JVM中，只有一个对象的实例，这有如下的好处：
	1. 避免了对象的实例化过程，减少了类的实例化。
	2. 在对象不被需要时。不会浪费GC。
	3. 有些类如交易所的核心交易引擎，控制着交易流程，如果该类可以创建多个的话，系统完全乱了。（比如一个军队出现了多个司令员同时指挥，肯定会乱成一团），所以只有使用单例模式，才能保证核心交易服务器独立控制整个流程。

### 线程不安全版本
```Java
public class Singleton implements Serializable{
	private static Singleton instance = null;
	private Singleton(){	//私有化构造器，这样保证了外部不会实例化该类。
	}
	public static Singleton getInstance(){
		if(null == instance){
			instance =  new Singleton();	//如果是第一次初始化，则新建一个对象。否则直接返回对象。
		}
		return instance;
	}
	public Object readResolve() {  //实现序列化
        return instance;
    }
}
```

### 线程安全版本1
> 将获取实例的过程上锁。或者说，这个方法将这个实例上锁了，范围较大。

```Java
	public static synchronized Singleton getInstance(){
		if(null == instance){
			instance =  new Singleton();
		}
		return instance;
	}
```

### 线程安全版本2
> 只需要在生成实例的时候上锁。这种方法减少了锁力度，并保证了线程安全。

```Java
	public static Singleton getInstance(){
		if(null == instance){
			synchronized (instance) {
				instance = new Singleton();
			}
		}
		return instance;
	}
```

### Reference
1. [23种设计模式全解析](https://www.cnblogs.com/susanws/p/5510229.html)
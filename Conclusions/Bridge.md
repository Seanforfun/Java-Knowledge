# Bridge Pattern 桥接模式
> 桥接模式即将抽象部分与它的实现部分分离开来，使他们都可以独立变化。
> 桥接模式将继承关系转化成关联关系，它降低了类与类之间的耦合度，减少了系统中类的数量，也减少了代码量。

### Bridge Pattern的使用场景
![Bridge Pattern](https://i.imgur.com/HxSWaRH.png)

1. 在这张图中，DriverManager就是一座桥。其持有一个Driver对象的实例（Driver是一个接口）。
2. Driver是一个接口，所以我们可以定义多个实现类(MysqlDriver, OracleDriver, DB2Driver)。
3. 我们可以替换桥接类中所持有的实例，这样就可以实现模式的替换，进行解耦。

### Bridge Pattern的实现
1. 定义一个接口
```Java
public interface Sourcable {	//定义了一个接口
	public void method();
}
```

2. 定义多个实现类，用于多个方面的替换
```Java
public class Source1 implements Sourcable {	//定义接口的多个实现类。
	@Override
	public void method() {
		System.out.println("This is method1...");
	}
}
public class Source2 implements Sourcable {
	@Override
	public void method() {
		System.out.println("This is method2...");
	}
}
```

3. 定义桥接类
```Java
public class Bridge implements Sourcable{
	private Sourcable source;	//通过接口持有多个实例。为实例提供get,set方法，用于解耦。因为是通过接口持有的对象，可以利用多态传入多种实现类。
	public Sourcable getSource() {
		return source;
	}
	public void setSource(Sourcable source) {
		this.source = source;
	}
	@Override
	public void method() {
		source.method();
	}
	public static void main(String[] args) {
		Bridge bridge = new Bridge();
		bridge.setSource(new Source1());	//通过设置持有的实例，实现多态。
		bridge.method();
		bridge.setSource(new Source2());
		bridge.method();
	}
}
This is method1...
This is method2...
```

### 总结
1.桥接模式实现了抽象化与实现化的脱耦。他们两个互相独立，不会影响到对方。
2.对于两个独立变化的维度，使用桥接模式再适合不过了。
3.对于“具体的抽象类”所做的改变，是不会影响到客户。
# Adapter Pattern aka Wrapper
>适配器模式将某个类的接口转换成客户端期望的另一个接口表示，目的是消除由于接口不匹配所造成的类的兼容性问题。主要分为三类：类的适配器模式、对象的适配器模式、接口的适配器模式。

### 类的适配器模式
![类的适配器模式](https://i.imgur.com/e13yJVh.png)
>我们已经有的类是Source类，而需要的则是一个Targetable类，我们通过适配器将Source转化成Target类。
>当我们要访问的接口A中没有我们想要的方法 ，却在另一个接口B中发现了合适的方法，我们又不能改变访问接口A，在这种情况下，我们可以定义一个适配器p来进行中转，这个适配器p要实现我们访问的接口A，这样我们就能继续访问当前接口A中的方法（虽然它目前不是我们的菜），然后再继承接口B的实现类BB，这样我们可以在适配器P中访问接口B的方法了，这时我们在适配器P中的接口A方法中直接引用BB中的合适方法，这样就完成了一个简单的类适配器。

1. 我们已经有了一个方法的实现类，其中含有method1方法，但是这个方法不满足需求（需要method2用到方法）
```Java
public class Source {
	public void method1(){
		System.out.println("Realize the method1...");
	}
}
```

2. Targetable使我们目标的类，我们希望实现这个类的功能，但是我们又不想重新实现这个接口的所有方法。
```Java
public interface Targetable {
	/**
	 * @Description method1 is already implemented in Source
	 * @Return: void
	 */
	public void method1();
	/**
	 * @Description Another method which is really necessary
	 * @Return: void
	 */
	public void method2();
}
```

3. 继承了Source方法，实现了Target类。
```Java
public class Adapter extends Source implements Targetable {
	@Override
	public void method2() {
		System.out.println("This is method2...");
	}
}
```

4. 实际上Adapter实现了Targetable，通过继承实现了方法的复用。自己实现了未实现的方法。
```Java
public class TargetTest1 {
	public static void main(String[] args) {
		Targetable target = new Adapter();
		target.method1();
		target.method2();
	}
}
```

### 对象的适配器模式
![对象的适配器模式](https://i.imgur.com/xofbZUG.png)
```Java
public class Wrapper implements Targetable {
	private Source source;	//内涵了一个source对象。
	public Wrapper(Source source){
		this.source = source;
	}
	@Override
	public void method1() {	//通过source对象复用已经实现的方法。
		source.method1();
	}
	@Override
	public void method2() {	//自己实现method2方法。
		System.out.println("This is method2...");
	}
}
```

### 接口的适配器模式
![接口的适配器模式](https://i.imgur.com/nyRh7CO.png)
> 有事后接口中定义了过多的方法，然而调用有时候我们并不需要如此多的方法，但是我们我们却要实现所有的方法才能生成出对象的实例，所以我们实现一个抽象类，实现了部分通用的方法，在我们生成接口的实例时，我们不必实现所有的方法，只需要继承抽象类。重写我们需要修改的接口。

1. 定义了一个接口，内部定义了多个方法。
```Java
public interface Sourcable {
	public void method1();
	public void method2();
}
```

2. 实现一个抽象类，内部实现一些通用的方法，一些独立性较强的类留着不实现。
```Java
public abstract class SourceWrapper implements Sourcable {
	@Override
	public void method1() {
	}
	@Override
	public void method2() {
	}
}
```

3. 实现专属的类重写并且实现某些方法,这样就避免了实现所有的方法。
```Java
public class SourceSub1 extends SourceWrapper {
	@Override
	public void method1() {
		System.out.println("This is a implementation of method1...");
	}
}
public class SourceSub2 extends SourceWrapper{
	@Override
	public void method2() {
		System.out.println("This is a implementation of method2...");
	}
}
```

### 总结
类的适配器模式：当希望将一个类转换成满足另一个新接口的类时，可以使用类的适配器模式，创建一个新类，继承原有的类，实现新的接口即可。

对象的适配器模式：当希望将一个对象转换成满足另一个新接口的对象时，可以创建一个Wrapper类，持有原类的一个实例，在Wrapper类的方法中，调用实例的方法就行。

接口的适配器模式：当不希望实现一个接口中所有的方法时，可以创建一个抽象类Wrapper，实现所有方法，我们写别的类的时候，继承抽象类即可。
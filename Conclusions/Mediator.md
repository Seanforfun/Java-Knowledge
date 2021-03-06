# Mediator Pattern 中介模式
> 对象之间的交互是很复杂的，可能会造成非常复杂的网状结构。不利于日后的扩展。
> 使用Mediator Pattern可以创造一个中介对象，至此所有对象不会直接交互，而是会通过和中介对象交互代为传达信号，所有的交互网络从网状结构变成星形结构。

![Mediator Pattern](https://i.imgur.com/Uqx4daS.png)

### Mediator中的角色
1. Mediator: 抽象中介者角色，定义了同事对象到中介者对象的接口，一般以抽象类的方式实现。
2. ConcreteMediator：具体中介者角色，继承于抽象中介者，实现了父类定义的方法，它从具体的同事对象接受消息，向具体同事对象发出命令。
3. Colleague：抽象同事类角色，定义了中介者对象的接口，它只知道中介者而不知道其他的同事对象。
4. ConcreteColleague1、ConcreteColleague2：具体同事类角色，继承于抽象同事类，每个具体同事类都知道本身在小范围的行为，而不知道在大范围内的目的。

### Mediator Pattern的实现
1. 中介者
```Java
public abstract class Mediator {
	protected HashMap<String, AbstractColleague> map = new HashMap<>();
	public abstract void execute(String name, Method method) throws Exception;	//定义了一个抽象的execute方法，用于交互。
	public void addColleague(String name, AbstractColleague c){
		c.setMediator(this);
		map.put(name, c);
	}
}
public class ConcreteMediator extends Mediator{
	@Override
	public void execute(String name, Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		super.map.get(name).self();
		method.invoke(super.map.get(name), null);	//实际的调用方法，其实可以不用反射。
	}
	public static void main(String[] args) throws Exception {
		ConcreteMediator mediator = new ConcreteMediator();
		ColleagueA a = new ColleagueA();
		mediator.addColleague("a", a);
		ColleagueB b = new ColleagueB();
		b.setMediator(mediator);
		mediator.addColleague("b", b);
		b.out();
	}
}
```

2. 交互的对象
```Java
public abstract class AbstractColleague {
	protected Mediator mediator;
	public void setMediator(Mediator mediator) {	//注册中介者
		this.mediator = mediator;
	}
	public abstract void self();
	public abstract void out() throws Exception;
}
public class ColleagueA extends AbstractColleague {
	public void self(){
		System.out.println("Colleague A -> do self.");
	}
	@Override
	public void out() throws Exception{
		System.out.println("Colleague A-> Colleague B please help!");
		super.mediator.execute("b", mediator.map.get("b").getClass().getDeclaredMethod("self", null));
	}
}
public class ColleagueB extends AbstractColleague {
	public void self(){
		System.out.println("Colleague B -> do self.");
	}
	public void out() throws Exception{
		System.out.println("Colleague B-> Colleague A please help!");
		super.mediator.execute("a", mediator.map.get("a").getClass().getDeclaredMethod("self", null));
	}
}
```

### Conclusion
1. 适当地使用中介者模式可以避免同事类之间的过度耦合，使得各同事类之间可以相对独立地使用。
2. 使用中介者模式可以将对象的行为和协作进行抽象，能够比较灵活的处理对象间的相互作用。
3. 使用中介者模式可以将对象间多对多的关联转变为一对多的关联，使对象间的关系易于理解和维护。

### Reference
1. [设计模式学习之中介者模式](https://blog.csdn.net/u012124438/article/details/70474166)
package ca.mcmaster.overload;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 16, 2018 9:34:07 PM
 * @version 1.0
 */
public class OverloadTest {
	static abstract class Human{
	}
	static class Man extends Human{
	}
	static class Woman extends Human{
	}
	public void sayHello(Human guy){
		System.out.println("Hello guy!");
	}
	public void sayHello(Man guy){
		System.out.println("Hello gentleman!");
	}
	public void sayHello(Woman guy){
		System.out.println("Hello lady!");
	}
	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		OverloadTest test = new OverloadTest();
		test.sayHello(man);
		test.sayHello(woman);
	}
}

package ca.mcmaster.overide;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 16, 2018 10:43:56 PM
 * @version 1.0
 */
public class OverrideTest {
	static abstract class Human{
		public abstract void sayHello();
	}
	static class Man extends Human{
		@Override
		public void sayHello() {
			System.out.println("Man says Hello.");
		}
	}
	static class Woman extends Human{
		@Override
		public void sayHello() {
			System.out.println("Woman says hello!");
		}
	}
	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		man.sayHello();
		woman.sayHello();
		man = new Woman();
		man.sayHello();
	}
}

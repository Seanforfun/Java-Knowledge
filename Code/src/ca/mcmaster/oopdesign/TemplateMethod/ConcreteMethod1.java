package ca.mcmaster.oopdesign.TemplateMethod;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 1, 2018 4:06:27 PM
 * @version 1.0
 */
public class ConcreteMethod1 extends TemplateClass {
	@Override
	public void action1() {
		System.out.println("This is action1 from concrete method1...");
	}
	@Override
	public void action2() {
		System.out.println("This is action2 from concrete method1...");
	}
}

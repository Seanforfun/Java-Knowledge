package ca.mcmaster.oopdesign.TemplateMethod;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 1, 2018 4:07:35 PM
 * @version 1.0
 */
public class ConcreteMethod2 extends TemplateClass {
	@Override
	public void action1() {
		System.out.println("This is action1 from concrete method2...");
	}
	@Override
	public void action2() {
		System.out.println("This is action2 from concrete method2...");
	}
	public static void main(String[] args) {
		TemplateClass template = new ConcreteMethod1();
		template.action1();
		template.action2();
		template = new ConcreteMethod2();
		template.action1();
		template.action2();
	}
}

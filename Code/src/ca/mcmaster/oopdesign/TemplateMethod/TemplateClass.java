package ca.mcmaster.oopdesign.TemplateMethod;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 1, 2018 4:04:57 PM
 * @version 1.0
 */
public abstract class TemplateClass {
	public abstract void action1();
	public abstract void action2();
	public final void templateAction(){
		action1();
		action2();
	}
}

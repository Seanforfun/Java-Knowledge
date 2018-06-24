package ca.mcmaster.oopdesign.adapter;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 24, 2018 7:15:08 PM
 * @version 1.0
 */
public class Wrapper implements Targetable {
	private Source source;
	public Wrapper(Source source){
		this.source = source;
	}
	@Override
	public void method1() {
		source.method1();
	}
	@Override
	public void method2() {
		System.out.println("This is method2...");
	}
}

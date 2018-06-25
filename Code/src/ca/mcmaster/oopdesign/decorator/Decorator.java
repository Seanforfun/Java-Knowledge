package ca.mcmaster.oopdesign.decorator;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 24, 2018 8:12:15 PM
 * @version 1.0
 */
public class Decorator implements Sourcable{
	private Sourcable source;
	public Decorator(Sourcable source){
		this.source = source;
	}
	private void adviceBefore(){
		System.out.println("This is method before...");
	}
	private void adviceAfter(){
		System.out.println("This is method after...");
	}
	@Override
	public void method() {
		adviceBefore();
		source.method();
		adviceAfter();
	}
	public static void main(String[] args) {
		Decorator decorator = new Decorator(new Sourcable() {
		});
		decorator.method();
	}
}

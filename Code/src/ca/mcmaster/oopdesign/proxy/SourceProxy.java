package ca.mcmaster.oopdesign.proxy;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 24, 2018 9:03:31 PM
 * @version 1.0
 */
public class SourceProxy implements Sourcable{
	private final Sourcable source;
	public SourceProxy(){
		source = new Source();
	}
	public void before(){
		System.out.println("Advice before...");
	}
	public void after(){
		System.out.println("Advice after...");
	}
	@Override
	public void method() {
		before();
		source.method();
		after();
	}
	public static void main(String[] args) {
		Sourcable s = new SourceProxy();
		s.method();
	}
}

package ca.mcmaster.oopdesign.strategy;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 26, 2018 1:17:20 PM
 * @version 1.0
 */
public class Context {
	private Strategy strategy;
	public Context(Strategy strategy){
		this.strategy = strategy;
	}
	public void contextInterface(){
		strategy.method();
	}
	public static void main(String[] args) {
		new Context(new ConcreteStrategy1()).contextInterface();
		new Context(new ConcreteStrategy2()).contextInterface();
	}
}

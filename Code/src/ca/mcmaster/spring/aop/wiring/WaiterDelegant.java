package ca.mcmaster.spring.aop.wiring;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 20, 2018 11:13:11 AM
 * @version 1.0
 */
public class WaiterDelegant {
	private Waiter waiter;
	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}
	public void service(String clientName){
		waiter.greetTo(clientName);
		waiter.serveTo(clientName);
	}
}

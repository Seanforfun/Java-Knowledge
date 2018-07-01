package ca.mcmaster.oopdesign.observer;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 1, 2018 6:09:11 PM
 * @version 1.0
 */
public class User2 implements Observer {
	private String message;
	@Override
	public void update(String s) {
		this.message = s;
	}
	public void loadMessage(){
		System.out.println(this.message);
	}
}

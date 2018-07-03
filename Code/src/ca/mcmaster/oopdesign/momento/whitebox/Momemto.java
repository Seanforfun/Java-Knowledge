package ca.mcmaster.oopdesign.momento.whitebox;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 3, 2018 11:22:25 AM
 * @version 1.0
 */
public class Momemto {
	private int state;
	public Momemto(int state){
		this.state = state;
		System.out.println("Momento: Saved state - " + state);
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}

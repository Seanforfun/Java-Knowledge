package ca.mcmaster.oopdesign.momento.whitebox;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 3, 2018 11:29:48 AM
 * @version 1.0
 */
public class Originator {
	private int state = 0;
	public Momemto createMomento(){
		return new Momemto(state);
	}
	public void restoreMomento(Momemto momemto){
		this.state = momemto.getState();
		System.out.println("Originator: Restored state - " + this.state + " from momemto.");
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public static void main(String[] args) {
		Originator originator = new Originator();
		Momemto momento = originator.createMomento();
		CareTaker careTaker = new CareTaker();
		careTaker.setMomemto(momento);
		originator.setState(1);
		System.out.println("Current state: " + originator.getState());
		originator.restoreMomento(careTaker.getMomemto());
		System.out.println("Current state: " + originator.getState());
	}
}

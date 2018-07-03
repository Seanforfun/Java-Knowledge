package ca.mcmaster.oopdesign.momento.blackbox;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 3, 2018 12:13:46 PM
 * @version 1.0
 */
public class Test {
	public static void main(String[] args) {
		Originator originator = new Originator();
		CareTaker ct = new CareTaker();
		IMomento momento = ct.getMomento();		
		ct.setMomento(originator.createMomento());
		originator.setState(3);
		System.out.println("Current state: " + originator.getState());
		originator.restoreMomento(ct.getMomento());
		System.out.println("Current state: " + originator.getState());
	}
}

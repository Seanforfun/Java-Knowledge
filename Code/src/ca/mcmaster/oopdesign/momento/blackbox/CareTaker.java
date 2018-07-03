package ca.mcmaster.oopdesign.momento.blackbox;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 3, 2018 12:05:47 PM
 * @version 1.0
 */
public class CareTaker {
	private IMomento momento;
	public IMomento getMomento() {
		return momento;
	}
	public void setMomento(IMomento momento) {
		this.momento = momento;
	}
}

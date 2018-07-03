package ca.mcmaster.oopdesign.momento.blackbox;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 3, 2018 11:59:45 AM
 * @version 1.0
 */
public class Originator {
	private int state = 0;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Momento createMomento(){
		return new Momento(this.state);
	}
	public void restoreMomento(IMomento momento){
		this.setState(((Momento)momento).getState());
	}
	private class Momento implements IMomento{
		private int state;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public Momento(int state){
			this.state = state;
			System.out.println("Momento: Saved state - " + state);
		}
	}
}

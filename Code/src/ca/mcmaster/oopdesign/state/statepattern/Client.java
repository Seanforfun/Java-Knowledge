package ca.mcmaster.oopdesign.state.statepattern;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 4, 2018 11:03:41 AM
 * @version 1.0
 */
public class Client {
	public static void main(String[] args) {
		Context context = new Context();
		context.setLiftState(new CloseState());
		context.open();
		context.close();
		context.run();
		context.stop();
	}
}

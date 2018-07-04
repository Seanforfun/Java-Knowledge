package ca.mcmaster.oopdesign.state.statepattern;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 4, 2018 10:37:20 AM
 * @version 1.0
 */
public class OpenState extends AbstractLift {
	@Override
	public void open() {
		System.out.println("Elevator opens.");
	}
	@Override
	public void close() {
		context.setLiftState(Context.CLOSE_STATE);
		context.getLiftState().close();
	}
	@Override
	public void run() {
	}
	@Override
	public void stop() {
	}
}

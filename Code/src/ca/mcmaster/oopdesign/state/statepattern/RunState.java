package ca.mcmaster.oopdesign.state.statepattern;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 4, 2018 10:39:05 AM
 * @version 1.0
 */
public class RunState extends AbstractLift {
	@Override
	public void open() {
	}
	@Override
	public void close() {
	}
	@Override
	public void run() {
		System.out.println("Elevator runs.");
	}
	@Override
	public void stop() {
		context.setLiftState(Context.STOP_STATE);
		context.getLiftState().stop();
	}
}

package ca.mcmaster.oopdesign.state.statepattern;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 4, 2018 10:39:05 AM
 * @version 1.0
 */
public class CloseState extends AbstractLift {
	@Override
	public void open() {
		context.setLiftState(Context.OPEN_STATE);
		context.getLiftState().open();
	}
	@Override
	public void close() {
		System.out.println("Elevator close.");
	}
	@Override
	public void run() {
		context.setLiftState(Context.RUN_STATE);
		context.getLiftState().run();
	}
	@Override
	public void stop() {
		context.setLiftState(Context.STOP_STATE);
		context.getLiftState().stop();
	}
}

package ca.mcmaster.oopdesign.state.statepattern;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 4, 2018 10:39:05 AM
 * @version 1.0
 */
public class StopState extends AbstractLift {
	@Override
	public void open() {
		super.context.setLiftState(Context.OPEN_STATE);
		super.context.getLiftState().open();
	}
	@Override
	public void close() {
	}
	@Override
	public void run() {
		super.context.setLiftState(Context.RUN_STATE);
		super.context.getLiftState().run();
	}
	@Override
	public void stop() {
		System.out.println("Elevator stops.");
	}
}

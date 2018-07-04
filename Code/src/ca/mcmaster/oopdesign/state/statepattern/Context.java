package ca.mcmaster.oopdesign.state.statepattern;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 4, 2018 10:33:58 AM
 * @version 1.0
 */
public class Context extends AbstractLift{
	public final static OpenState OPEN_STATE = new OpenState();
	public final static CloseState CLOSE_STATE = new CloseState();
	public final static RunState RUN_STATE = new RunState();
	public final static StopState STOP_STATE = new StopState();
	private AbstractLift liftState;
	public AbstractLift getLiftState() {
		return liftState;
	}
	public void setLiftState(AbstractLift liftState) {
		this.liftState = liftState;
		this.liftState.setContext(this);
	}
	@Override
	public void open() {
		liftState.open();
	}
	@Override
	public void close() {
		liftState.close();
	}
	@Override
	public void run() {
		liftState.run();
	}
	@Override
	public void stop() {
		liftState.stop();
	}
}

package ca.mcmaster.oopdesign.state.statepattern;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 4, 2018 10:32:12 AM
 * @version 1.0
 */
public abstract class AbstractLift implements ILiftState {
	protected Context context;
	public void setContext(Context context){
		this.context = context;
	}
}

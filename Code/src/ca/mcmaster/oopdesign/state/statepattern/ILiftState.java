package ca.mcmaster.oopdesign.state.statepattern;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 4, 2018 10:30:23 AM
 * @version 1.0
 */
public interface ILiftState {
	public void open();
	public void close();
	public void run();
	public void stop();
}

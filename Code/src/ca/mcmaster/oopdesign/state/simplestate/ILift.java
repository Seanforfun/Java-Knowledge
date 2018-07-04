package ca.mcmaster.oopdesign.state.simplestate;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 3, 2018 5:31:46 PM
 * @version 1.0
 */
public interface ILift {
	public static final int OPENING_STATE = 1;
	public static final int CLOSE_STATE = 2;
	public static final int RUNNING_STATE = 3;
	public static final int STOPPING_STATE = 4;
	/**
	 * @Description: Set state of current object.
	 * @Return: void
	 */
	public void setState(int state);
	public void open();
	public void close();
	public void run();
	public void stop();
}

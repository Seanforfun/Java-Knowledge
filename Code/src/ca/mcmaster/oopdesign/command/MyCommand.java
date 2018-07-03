package ca.mcmaster.oopdesign.command;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 3, 2018 10:04:20 AM
 * @version 1.0
 */
public class MyCommand implements ICommand {
	private Receiver receiver;
	public MyCommand(Receiver receiver) {
		this.receiver = receiver;
	}
	@Override
	public void exec() {
		this.receiver.action();
	}
}

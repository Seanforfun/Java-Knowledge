package ca.mcmaster.oopdesign.command;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 3, 2018 10:06:39 AM
 * @version 1.0
 */
public class Invoker {
	private ICommand command;
	public Invoker(ICommand command){
		this.command = command;
	}
	public void invoke(){
		command.exec();
	}
	public static void main(String[] args) {
		Receiver receiver = new Receiver();
		ICommand command = new MyCommand(receiver);
		Invoker invoker = new Invoker(command);
		invoker.invoke();
	}
}

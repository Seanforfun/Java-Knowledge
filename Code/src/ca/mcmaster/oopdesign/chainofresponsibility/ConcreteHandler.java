package ca.mcmaster.oopdesign.chainofresponsibility;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 2, 2018 3:12:14 PM
 * @version 1.0
 */
public class ConcreteHandler extends AbstractHandler {
	public ConcreteHandler(Integer level, AbstractHandler nextHandler) {
		super.nextHandler = nextHandler;
		super.level = level;
	}
	@Override
	public void doHandler(Integer level, Task task) {
		System.out.println(level + ": finish the task " + task.getTaskName());
	}
	public static void main(String[] args) {
		AbstractHandler handler3 = new ConcreteHandler(3, null);
		AbstractHandler handler2 = new ConcreteHandler(2, handler3);
		AbstractHandler handler1 = new ConcreteHandler(1, handler2);
		AbstractHandler handler = new ConcreteHandler(0, handler1);
		Task task = new Task(4, "A task!");
		try {
			handler.handle(task);
		} catch (Exception e) {
			System.out.println("No one can handle this task!");
		}
	}
}

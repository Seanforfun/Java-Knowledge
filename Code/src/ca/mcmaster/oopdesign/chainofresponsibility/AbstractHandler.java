package ca.mcmaster.oopdesign.chainofresponsibility;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 2, 2018 3:11:04 PM
 * @version 1.0
 */
public abstract class AbstractHandler implements Handler {
	protected Integer level;
	protected AbstractHandler nextHandler;
	@Override
	public void handle(Task task) throws Exception {
		if(this.level == task.getLevel()){
			doHandler(this.level, task);
		}else{
			if(null != nextHandler){
				try {
					nextHandler.handle(task);
				} catch (Exception e) {
					System.out.println(nextHandler.level + ": Not able to handle the task!");
					throw new Exception();
				}
			}
			else
				throw new Exception("No one can deal with this task!");
		}
	}
	public abstract void doHandler(Integer level, Task task);
}

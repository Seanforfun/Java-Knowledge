package ca.mcmaster.oopdesign.chainofresponsibility;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 2, 2018 3:10:02 PM
 * @version 1.0
 */
public class Task {
	private Integer level;
	private String taskName;
	public Task(Integer level, String task){
		this.level = level;
		this.taskName = task;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}

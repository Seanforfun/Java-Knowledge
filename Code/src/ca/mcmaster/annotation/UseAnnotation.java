package ca.mcmaster.annotation;

import ca.mcmaster.annotation.Todo.Priority;
import ca.mcmaster.annotation.Todo.Status;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 20, 2018 11:54:40 AM
 * @version 1.0
 */
public class UseAnnotation {
	@Todo(priority = Priority.LOW, author = "Botao Xiao", status = Status.STARTED)
	public void run(){
		System.out.println("I am not finish.");
	}
}

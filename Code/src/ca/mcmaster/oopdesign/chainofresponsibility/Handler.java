package ca.mcmaster.oopdesign.chainofresponsibility;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 2, 2018 3:09:14 PM
 * @version 1.0
 */
public interface Handler {
	public void handle(Task task) throws Exception;
}

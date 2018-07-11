package ca.mcmaster.oopdesign.visitor;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 11, 2018 3:13:17 PM
 * @version 1.0
 */
public interface Bill {	//Element的抽象类
	public void accept(AccountBillViewer viewer);
}

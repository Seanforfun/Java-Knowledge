package ca.mcmaster.oopdesign.visitor;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 11, 2018 3:14:42 PM
 * @version 1.0
 */
public interface AccountBillViewer {
	public void view(ConsumerBill bill);
	public void view(IncomeBill bill);
}

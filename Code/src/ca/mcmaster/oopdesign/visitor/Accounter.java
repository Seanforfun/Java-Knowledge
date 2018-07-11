package ca.mcmaster.oopdesign.visitor;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 11, 2018 3:26:49 PM
 * @version 1.0
 */
public class Accounter implements AccountBillViewer {

	@Override
	public void view(ConsumerBill bill) {
		if(bill.getItem().equals("salary"))
			System.out.println("Check comsume...");
	}
	@Override
	public void view(IncomeBill bill) {
		if(bill.getItem().equals("income"))
			System.out.println("Check Income...");
	}

}

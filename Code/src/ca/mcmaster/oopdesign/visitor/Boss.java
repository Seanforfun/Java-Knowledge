package ca.mcmaster.oopdesign.visitor;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 11, 2018 3:21:40 PM
 * @version 1.0
 */
public class Boss implements AccountBillViewer {
	private double totalIncome;
	private double totalConsume;
	@Override
	public void view(ConsumerBill bill) {
		totalConsume += bill.getAmount();
	}
	@Override
	public void view(IncomeBill bill) {
		this.totalIncome += bill.getAmount();
	}
	public double getTotalConsume() {
		System.out.println("Boss: spend " + this.totalConsume + " dollars");
		return totalConsume;
	}
	public double getTotalIncome() {
		System.out.println("Boss: get " + this.totalIncome + " dollars");
		return totalIncome;
	}
}

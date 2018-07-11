package ca.mcmaster.oopdesign.visitor;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 11, 2018 3:18:12 PM
 * @version 1.0
 */
public class IncomeBill implements Bill {
	private Double amount;
	private String item;
	public Double getAmount() {
		return amount;
	}
	public String getItem() {
		return item;
	}
	public IncomeBill(Double amount, String item) {
		this.amount = amount;
		this.item = item;
	}
	@Override
	public void accept(AccountBillViewer viewer) {
		viewer.view(this);
	}
}

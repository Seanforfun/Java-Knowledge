package ca.mcmaster.oopdesign.visitor;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 11, 2018 3:15:27 PM
 * @version 1.0
 */
public class ConsumerBill implements Bill {
	private Double amount;
	private String item;
	public String getItem() {
		return item;
	}
	public Double getAmount() {
		return amount;
	}
	public ConsumerBill(Double amount, String item) {
		this.amount = amount;
		this.item = item;
	}
	@Override
	public void accept(AccountBillViewer viewer) {
		viewer.view(this);
	}
}

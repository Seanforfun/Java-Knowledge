package ca.mcmaster.oopdesign.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 11, 2018 3:30:28 PM
 * @version 1.0
 */
public class AccountBook {
	private List<Bill> billList = new ArrayList<>();
	public void addBill(Bill bill){
		this.billList.add(bill);
	}
	public void show(AccountBillViewer viewer){
		for(Bill bill : billList){
			bill.accept(viewer);
		}
	}
	public static void main(String[] args) {
		AccountBook accountBook = new AccountBook();
		accountBook.addBill(new IncomeBill(100d, "a"));
		accountBook.addBill(new IncomeBill(200d, "b"));
		accountBook.addBill(new ConsumerBill(100d, "c"));
		accountBook.addBill(new ConsumerBill(200d, "d"));
		AccountBillViewer boss = new Boss();
		AccountBillViewer accounter = new Accounter();
		accountBook.show(boss);
		accountBook.show(accounter);
		((Boss)boss).getTotalConsume();
		((Boss)boss).getTotalIncome();
	}
}

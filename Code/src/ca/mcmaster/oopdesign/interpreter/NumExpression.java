package ca.mcmaster.oopdesign.interpreter;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 15, 2018 10:51:36 AM
 * @version 1.0
 */
public class NumExpression implements Expression {
	private int num;
	public NumExpression(int num) {
		this.num = num;
	}
	@Override
	public int interpret() {
		return this.num;
	}
}

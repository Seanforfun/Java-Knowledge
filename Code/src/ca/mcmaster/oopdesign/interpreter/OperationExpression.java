package ca.mcmaster.oopdesign.interpreter;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 15, 2018 10:53:35 AM
 * @version 1.0
 */
public abstract class OperationExpression implements Expression {
	protected NumExpression numExpression1;
	protected NumExpression numExpression2;
	public OperationExpression(NumExpression numExpression1, NumExpression numExpression2) {
		this.numExpression1 = numExpression1;
		this.numExpression2 = numExpression2;
	}
}

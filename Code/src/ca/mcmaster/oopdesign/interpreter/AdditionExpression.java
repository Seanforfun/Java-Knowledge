package ca.mcmaster.oopdesign.interpreter;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 15, 2018 10:56:07 AM
 * @version 1.0
 */
public class AdditionExpression extends OperationExpression {

	public AdditionExpression(NumExpression numExpression1,
			NumExpression numExpression2) {
		super(numExpression1, numExpression2);
	}
	@Override
	public int interpret() {
		return super.numExpression1.interpret() + super.numExpression2.interpret();
	}
}

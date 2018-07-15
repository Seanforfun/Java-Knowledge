package ca.mcmaster.oopdesign.interpreter;

import java.util.Stack;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 15, 2018 10:57:46 AM
 * @version 1.0
 */
public class Calculator {
	protected Stack<NumExpression> numExpressionStack = new Stack<>();
	
	public Calculator(String expression){
		NumExpression numExpression1, numExpression2;
		String[] tokens = expression.split(" ");
		for(int i = 0; i < tokens.length; i++){
			switch (tokens[i].charAt(0)) {
			case '+':
				numExpression1 = numExpressionStack.pop();
				numExpression2 = new NumExpression(Integer.valueOf(tokens[++i]));
				numExpressionStack.push(new NumExpression(new AdditionExpression(numExpression1, numExpression2).interpret()));
				break;

			default:
				numExpressionStack.push(new NumExpression(Integer.valueOf(tokens[i])));
				break;
			}
		}
	}
	
	public int calculate(){
		return this.numExpressionStack.pop().interpret();
	}
	
	public static void main(String[] args) {
		System.out.println(new Calculator("1 + 2 + 3").calculate());
	}
}

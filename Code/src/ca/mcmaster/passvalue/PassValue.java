package ca.mcmaster.passvalue;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 11, 2018 10:57:46 AM
 * @version 1.0
 */
public class PassValue {
	class Test{
		private Integer test = new Integer(0);
	}
	private int i = 0;
	public void change(Test t){
		t.test ++;
	}
	public static void main(String[] args) {
		Integer i = new Integer(0);
		PassValue passValue = new PassValue();
		Test test = passValue.new Test();
		passValue.change(test);
		System.out.println(test.test);
	}
}

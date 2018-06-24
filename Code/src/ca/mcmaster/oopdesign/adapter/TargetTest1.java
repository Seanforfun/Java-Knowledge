package ca.mcmaster.oopdesign.adapter;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 24, 2018 7:08:04 PM
 * @version 1.0
 */
public class TargetTest1 {
	public static void main(String[] args) {
		Targetable target = new Adapter();
		target.method1();
		target.method2();
		target = new Wrapper(new Source());
		target.method1();
		target.method2();
		Sourcable source1 = new SourceSub1();
		Sourcable source2 = new SourceSub2();
		source1.method1();
		source2.method2();
	}
}

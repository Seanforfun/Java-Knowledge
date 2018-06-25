package ca.mcmaster.oopdesign.bridge;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 25, 2018 11:32:48 AM
 * @version 1.0
 */
public class Bridge implements Sourcable{
	private Sourcable source;
	public Sourcable getSource() {
		return source;
	}
	public void setSource(Sourcable source) {
		this.source = source;
	}
	@Override
	public void method() {
		source.method();
	}
	public static void main(String[] args) {
		Bridge bridge = new Bridge();
		bridge.setSource(new Source1());
		bridge.method();
		bridge.setSource(new Source2());
		bridge.method();
	}
}

package ca.mcmaster.oopdesign.composite;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 25, 2018 12:15:29 PM
 * @version 1.0
 */
public class VedioFile extends File {
	public VedioFile(String name) {
		setName(name);
	}
	@Override
	public void display() {
		System.out.println("This is a vedio file..." + super.getName());
	}
}

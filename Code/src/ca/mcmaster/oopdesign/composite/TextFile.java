package ca.mcmaster.oopdesign.composite;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 25, 2018 12:16:08 PM
 * @version 1.0
 */
public class TextFile extends File {
	public TextFile(String name){
		setName(name);
	}
	@Override
	public void display() {
		System.out.println("This is a text file..." + super.getName());
	}
}

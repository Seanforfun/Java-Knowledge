package ca.mcmaster.oopdesign.composite;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 25, 2018 12:10:13 PM
 * @version 1.0
 */
public abstract class File {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public abstract void display();
}

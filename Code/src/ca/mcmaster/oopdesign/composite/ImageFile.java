package ca.mcmaster.oopdesign.composite;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 25, 2018 12:14:39 PM
 * @version 1.0
 */
public class ImageFile extends File {
	public ImageFile(String name){
		setName(name);
	}
	@Override
	public void display() {
		System.out.println("This is a image file..." + super.getName());
	}
}

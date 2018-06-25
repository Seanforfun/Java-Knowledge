package ca.mcmaster.oopdesign.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 25, 2018 12:17:52 PM
 * @version 1.0
 */
public class Folder extends File{
	private final List<File> files;
	public Folder() {
		files = new ArrayList<File>();
	}
	public void add(File f){
		files.add(f);
	}
	public void remove(File f){
		files.remove(f);
	}
	@Override
	public void display() {
		for(File f : files){
			f.display();
		}
	}
	public static void main(String[] args) {
		Folder folder = new Folder();
		TextFile textFile = new TextFile("Test file");
		folder.add(textFile);
		ImageFile imageFile = new ImageFile("Image file");
		folder.add(imageFile);
		VedioFile vedioFile = new VedioFile("Vedio file");
		folder.add(vedioFile);
		folder.display();
	}
}

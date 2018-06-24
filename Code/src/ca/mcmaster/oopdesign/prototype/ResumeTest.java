package ca.mcmaster.oopdesign.prototype;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 24, 2018 6:17:38 PM
 * @version 1.0
 */
public class ResumeTest {
	public static void main(String[] args) {
		Resume resume1 = new Resume("Sean Xiao", "male");
		Resume resume2 = (Resume) resume1.clone();
		System.out.println(resume1.getName());
		resume2.setName("Botao Xiao");
		System.out.println(resume1.getName());
		System.out.println(resume2.getName());
	}
}

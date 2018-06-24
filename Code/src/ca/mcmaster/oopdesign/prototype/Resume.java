package ca.mcmaster.oopdesign.prototype;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 24, 2018 6:09:41 PM
 * @version 1.0
 * @Description Resume is a prototype, and it has to realize {@link Cloneable}
 */
public class Resume implements Cloneable {
	private String name;
	private String gender;
	public Resume(String name, String gender) {
		this.name = name;
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 * @Description 重写了Object的clone()方法，利用了父类的clone方法复制出一个全新的对象。 
	 */
	@Override
	protected Object clone(){
		Resume resume = null;
		try {
			resume = (Resume) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resume;
	}
}

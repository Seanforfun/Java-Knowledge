package ca.mcmaster.oopdesign.builder;
/**
 * @author SeanForFun
 * @date  Jun 22, 2018 3:25:33 PM
 * @Description 要生成实例的类。
 * 类的组成相对复杂，需要详细的流程指导类的初始化。
 * @version 1.0
 */
public class Car {
	private String wheel;
	private String engine;
	private String skeleton;
	public String getWheel() {
		return wheel;
	}
	public void setWheel(String wheel) {
		this.wheel = wheel;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public String getSkeleton() {
		return skeleton;
	}
	public void setSkeleton(String skeleton) {
		this.skeleton = skeleton;
	}
}

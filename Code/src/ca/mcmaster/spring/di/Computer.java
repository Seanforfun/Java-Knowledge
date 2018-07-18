package ca.mcmaster.spring.di;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 18, 2018 9:09:59 AM
 * @version 1.0
 */
@Component(value="computer")
@Scope("singleton")
public class Computer {
	@Value(value="MacBook")
	private String brand;
//	@Autowired
//	@Qualifier("student")
	private Student student;
	
	@Resource(name="colorList")
	private List<String> colorList;
	@Bean(name="colorList")
	public List<Student> testList(Student student){
		List<Student> testList = new ArrayList<>();
		testList.add(student);
		return testList;
	}
	public List<String> getColorList() {
		return colorList;
	}
	public void setColorList(List<String> colorList) {
		this.colorList = colorList;
	}
	public Student getStudent() {
		return student;
	}
	@Autowired
	public void setStudent(@Qualifier("student") Student student) {
		this.student = student;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void printNow(){
		System.out.println("Haha");
	}
	public void printStudent(){
		System.out.println(this.student);
	}
}

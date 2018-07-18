package ca.mcmaster.spring.di;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.mcmaster.spring.Customer;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 17, 2018 2:11:36 PM
 * @version 1.0
 */
public class Student {
	private String name;
	private int age;
	private List<String> hobbies = new ArrayList<>();
	private Set<String> set = new HashSet<>();
	private Map<String, String> map = new HashMap<String, String>();
	private Properties properties = new Properties();
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public Set<String> getSet() {
		return set;
	}
	public void setSet(Set<String> set) {
		this.set = set;
	}
	public List<String> getHobbies() {
		return hobbies;
	}
	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}
	private Customer customer = new Customer();
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml") ;
		Student student = (Student) ctx.getBean("student");
		System.out.println(student.getCustomer().getName());
		List<String> ho = student.getHobbies();
		for(String h : ho){
			System.out.println(h);
		}
		Set<String> set = student.getSet();
		for(String s:set){
			System.out.println(s);
		}
		Map<String, String> map = student.getMap();
		for(String s : map.keySet()){
			System.out.println(s);
		}
		Properties properties = student.getProperties();
		System.out.println(properties.get("name"));
	}
}

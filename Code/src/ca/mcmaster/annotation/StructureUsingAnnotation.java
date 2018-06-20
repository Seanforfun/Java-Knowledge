package ca.mcmaster.annotation;

import java.lang.reflect.Method;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 20, 2018 12:05:00 PM
 * @version 1.0
 */
public class StructureUsingAnnotation {
	public static void resolveTodo(){
		Class<UseAnnotation> clazz = UseAnnotation.class;
		Method[] methods = clazz.getDeclaredMethods();
		for(Method m : methods){
			if(m.getAnnotation(Todo.class) != null){
				System.out.println("Mehod: " + m.getName());
				Todo a = m.getAnnotation(Todo.class);
				System.out.println("Author: " + a.author());
				System.out.println("Status: " + a.status());
				System.out.println("Priority: " + a.priority());
			}
		}
	}
	public static void main(String[] args) {
		StructureUsingAnnotation.resolveTodo();
	}
}

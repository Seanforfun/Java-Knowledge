package ca.mcmaster.annotation;

import java.lang.reflect.Method;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 18, 2018 11:06:25 AM
 * @version 1.0
 */
public class CallAnnotation {
	
	/**
	 * @Description: Implement the annotation on method
	 */
	@TestAnnotation
	public void printSomething(){
		String author;
		Method[] methods = CallAnnotation.class.getDeclaredMethods();
		for(Method m : methods){
			if(m.isAnnotationPresent(TestAnnotation.class)){
				author = m.getAnnotation(TestAnnotation.class).author();
				System.out.println("Hello " + author);
			}
		}
	}
	
	public static void main(String[] args) {
		new CallAnnotation().printSomething();
	}
}

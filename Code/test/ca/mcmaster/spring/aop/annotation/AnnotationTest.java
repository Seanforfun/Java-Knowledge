package ca.mcmaster.spring.aop.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 25, 2018 11:00:36 AM
 * @version 1.0
 */
public class AnnotationTest {
	@Reviews({@Review(value=Grade.EXCELLENT, reviewer="Seanforfun"), 
		@Review(value=Grade.UNSATISFACTORY, reviewer="Sean")})
	public static void annotationFieldTest() throws Exception{
		Method testMethod = AnnotationTest.class.getDeclaredMethod("annotationFieldTest", null);
		Annotation[] annotations = testMethod.getDeclaredAnnotations();
		for(Annotation annotation : annotations){
			Method method = annotation.getClass().getDeclaredMethod("value", null);
			Review[] reviews = (Review[]) method.invoke(annotation, null);
			for(Review r : reviews){
				System.out.println(r);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		annotationFieldTest();
	}
}

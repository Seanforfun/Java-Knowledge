package ca.mcmaster.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Apr 18, 2018 11:00:32 AM
 * @version 1.0
 */
@Documented
@Target(value=ElementType.METHOD)	//Annotation can only be applied on methods
@Retention(RetentionPolicy.RUNTIME)	//This annotation will not be dropped and we can use java reflect to call this annotation
@Inherited														//Apply this annotation to child class
public @interface TestAnnotation {
	String author() default "Seanforfun";
	enum Level {LOW, MEDIUM, HIGH};
}

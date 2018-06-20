package ca.mcmaster.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 20, 2018 11:45:25 AM
 * @version 1.0
 */
@Target(value = { ElementType.METHOD })	//作用在方法上
@Documented()	//添加进入JavaDoc
@Retention(value = RetentionPolicy.RUNTIME)	//生命周期：运行时
@Inherited		//该注解可以被继承
public @interface Todo {
	public enum Priority{LOW, MEDIUM, HIGH};	//可以理解为定义了内部枚举类
	public enum Status{STARTED, NOT_STARTED, FINISHED};
	Priority priority() default Priority.MEDIUM;	//定义了方法，可以给出默认的返回值。
	String author() default "Seanforfun";
	Status status() default Status.NOT_STARTED;
}

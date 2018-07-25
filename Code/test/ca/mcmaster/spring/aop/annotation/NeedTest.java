package ca.mcmaster.spring.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 25, 2018 10:42:26 AM
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)	//定义了生命周期
@Target(ElementType.METHOD)	//定义了作用域
public @interface NeedTest {
	boolean value() default false;	//声明了参数
}

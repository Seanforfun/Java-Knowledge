package ca.mcmaster.spring.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 25, 2018 10:51:16 AM
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Reviews {
	Review[] value();
}

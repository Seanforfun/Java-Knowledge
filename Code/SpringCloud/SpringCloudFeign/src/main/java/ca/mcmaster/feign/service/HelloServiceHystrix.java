package ca.mcmaster.feign.service;

import org.springframework.stereotype.Component;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Oct 17, 2018 10:54:28 AM
 * @version 1.0
 */
@Component
public class HelloServiceHystrix implements HelloService {
	@Override
	public String sayHello(String name) {
		return "Hello" + name + ", Error happend!";
	}
}

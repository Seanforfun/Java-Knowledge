package ca.mcmaster.feign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcmaster.feign.service.HelloService;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Oct 16, 2018 5:48:03 PM
 * @version 1.0
 */
@RestController
public class HelloController {
	@Autowired
	private HelloService helloService;
	@RequestMapping("/hi")
	public String sayHi(@RequestParam String name){
		return helloService.sayHello(name);
	}
}

package ca.mcmaster.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Oct 18, 2018 2:21:30 PM
 * @version 1.0
 */
@RestController
public class HiController {
	@Value("${foo}")
	String foo;
	@RequestMapping("/hi")
	public String sayHi(){
		return foo;
	}
}

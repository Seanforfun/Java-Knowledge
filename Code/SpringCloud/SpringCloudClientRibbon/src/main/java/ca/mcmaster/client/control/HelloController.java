package ca.mcmaster.client.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcmaster.client.service.HelloService;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Oct 16, 2018 4:25:15 PM
 * @version 1.0
 */
@RestController
public class HelloController {
	@Autowired
	private HelloService helloService;
	
	@RequestMapping(value="/hi")
	public String hi(@RequestParam(required=true, value="name") String name){
		return helloService.hiService(name);
	}
}

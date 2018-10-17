package ca.mcmaster.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Oct 16, 2018 4:14:55 PM
 * @version 1.0
 */
@Service
public class HelloService {
	@Autowired
	private RestTemplate restTemplate;
	@HystrixCommand(fallbackMethod="hiError")
	public String hiService(String name){
		return restTemplate.getForObject("http://SERVICE-HI/hi?name=" + name, String.class);
	}
	public String hiError(String name){
		return "Sorry " + name + ", Error happened!";
	}
}

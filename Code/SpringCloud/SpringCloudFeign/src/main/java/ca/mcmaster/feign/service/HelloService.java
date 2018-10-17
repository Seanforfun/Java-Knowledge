package ca.mcmaster.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Oct 16, 2018 5:44:55 PM
 * @version 1.0
 */
//定义一个feign接口，通过@ FeignClient（“服务名”），来指定调用哪个服务。
@FeignClient(value="SERVICE-HI", fallback=HelloServiceHystrix.class)	
public interface HelloService {
	@RequestMapping(value="/hi")
	public String sayHello(@RequestParam("name") String name);
}

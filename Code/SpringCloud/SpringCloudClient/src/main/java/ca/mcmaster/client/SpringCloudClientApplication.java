package ca.mcmaster.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
public class SpringCloudClientApplication {
	@Value("${server.port}")
	private String port;
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudClientApplication.class, args);
	}
	@RequestMapping("/hi")
	private String sayHello(@RequestParam(required=true, value="name") String name){
		return "Hello " + name + ", " + "return from port: " + this.port;
	}
}

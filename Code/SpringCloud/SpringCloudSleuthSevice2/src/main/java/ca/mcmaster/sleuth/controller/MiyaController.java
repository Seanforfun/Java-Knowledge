package ca.mcmaster.sleuth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@RestController
public class MiyaController {
    private static Logger logger = Logger.getLogger(MiyaController.class.getName());
    @Autowired
    private RestTemplate restTemplate;
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @RequestMapping("/miya")
    public String sayHi(){
        logger.info("This is miya.");
        return restTemplate.getForObject("http://SERVICE-HI/info", String.class);
    }
    @RequestMapping("info")
    public String info(){
        return restTemplate.getForObject("http://localhost:8762/info", String.class);
    }
}

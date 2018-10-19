package ca.mcmaster.sleuth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@RestController
public class HiController {
        private static Logger logger = Logger.getLogger(HiController.class.getName());
        @Autowired
        private RestTemplate restTemplate;
        @Bean
        @LoadBalanced
        public RestTemplate restTemplate(){
            return new RestTemplate();
        }
        @RequestMapping("/hi")
        private String callHome(){
            logger.info("Calling http://localhost:8763/miya");
            return restTemplate.getForObject("http://SERVICE-MIYA/miya", String.class);
        }
        @RequestMapping("/info")
        private String info(){
            logger.info("I am service-hi.");
            return "I am service-hi.";
        }
}

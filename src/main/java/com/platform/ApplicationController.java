package com.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mat on 2017-03-27.
 */
@RestController
@RequestMapping(value = "${base_url}")
public class ApplicationController {

	final static Logger logger = LoggerFactory.getLogger(ApplicationController.class);
	
    @Value("${hello_response}")
    private String response;
    
    @Autowired
    private CounterService counterService;
    
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
    	logger.info("Saying hello info");
    	counterService.increment("demo.hello.request");
        return "Hi " + response + "!";
    }
}

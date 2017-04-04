package com.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
    	logger.debug("Saying hello debug");
    	logger.info("Saying hello info");
        return "Hi " + response + "!";
    }
}

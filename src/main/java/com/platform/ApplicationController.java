package com.platform;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mat on 2017-03-27.
 */
@RestController
public class ApplicationController {

    @Value("${hello_response}")
    private String response;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hi " + response + "!";
    }
}

package com.uken.platform.demo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Api(
  value = "demo",
  description = "Controller for serving a static web page"
) // This is for swagger
public class ExampleWebpageController {

  @RequestMapping(value = "/example", method = RequestMethod.GET)
  @ApiOperation(value = "examplePage", notes = "renders a simple html page")
  public String examplePage(Model model) {
    model.addAttribute("exampleData", "{\"greeting\": \"Hi from the backend!\"}");

    return "example_template"; // name of the template you want to load without an extension
  }
}

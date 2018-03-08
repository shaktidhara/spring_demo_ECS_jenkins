package com.uken.platform.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExampleWebpageController {

  @RequestMapping(value = "/example", method = RequestMethod.GET)
  public String issueQuestionPage(Model model) throws JsonProcessingException {
    model.addAttribute("exampleData", "{\"greeting\": \"Hi from the backend!\"}");

    return "example_template"; // name of the template you want to load without an extension
  }
}

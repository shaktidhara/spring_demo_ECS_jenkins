package com.uken.platform.demo.controllers;

import com.google.common.util.concurrent.AtomicDouble;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Random;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(
  value = "demo",
  description =
      "A sample controller that shows the use of custom metrics and application properties"
)
public class ExampleRestController {

  static final Logger logger = LoggerFactory.getLogger(ExampleRestController.class);

  @Value("${uken.hello_response}")
  private String response;

  @Autowired private MeterRegistry meterRegistry;
  private Counter helloCounter;
  private AtomicDouble helloGuage;

  @PostConstruct
  public void setup() {
    helloCounter = meterRegistry.counter("demo.hello.counter");
    helloGuage = meterRegistry.gauge("demo.hello.guage", new AtomicDouble(0));
  }

  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  @ApiOperation(value = "hello", notes = "return a response driven by application properties")
  public String hello() {
    logger.info("/hello called");

    helloGuage.set(new Random().nextDouble());
    helloCounter.increment();
    return "Hi " + response + "!";
  }
}

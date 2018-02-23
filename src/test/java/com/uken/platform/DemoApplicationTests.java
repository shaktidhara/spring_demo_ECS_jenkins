package com.uken.platform;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoApplicationTests {
  Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

  @Test
  public void contextLoads() throws InterruptedException {
    logger.info("Simulating long running tests...");
    TimeUnit.SECONDS.sleep(5);
    assertTrue(true);
  }
}

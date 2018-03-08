package com.uken.platform.demo.services;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

import com.uken.platform.demo.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test") // often it's helpful to have a separate profile for running tests.
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisServiceIT {

  @Autowired UserRepository userRepository;

  @Autowired RedisTemplate<String, String> redisTemplate;

  @Autowired RedisService redisService;

  @Before // before each test
  public void setup() {
    userRepository.deleteAll();
  }

  @Test
  public void setShouldSetTheValue() {
    String key = "mykey";
    String value = "myvalue";

    assertNull(redisTemplate.opsForValue().get(key)); // precondition

    redisService.set(key, value);

    assertEquals(value, redisTemplate.opsForValue().get(key));
  }
}

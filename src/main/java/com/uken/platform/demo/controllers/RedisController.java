package com.uken.platform.demo.controllers;

import com.uken.platform.demo.controllers.requests.RedisSetRequest;
import com.uken.platform.demo.services.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/redis")
@Api(value = "demo", description = "Endpoints for interacting with redis") // This is for swagger
public class RedisController {

  @Autowired RedisService redisService;

  @RequestMapping(
    value = "/set",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ApiOperation(value = "set", notes = "sets a simple value in redis")
  public String set(@RequestBody RedisSetRequest request) {
    redisService.set(request.getKey(), request.getValue());
    return "OK";
  }

  @RequestMapping(value = "/get/{key}", method = RequestMethod.GET)
  @ApiOperation(value = "get", notes = "get a value from redis")
  public String get(@PathVariable String key) {
    return redisService.get(key);
  }
}

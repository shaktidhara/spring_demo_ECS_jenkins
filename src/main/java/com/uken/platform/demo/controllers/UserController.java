package com.uken.platform.demo.controllers;

import com.uken.platform.demo.exceptions.UserNotFound;
import com.uken.platform.demo.models.User;
import com.uken.platform.demo.repositories.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
@Api(value = "demo", description = "Endpoints for user management") // This is for swagger
public class UserController {

  @Autowired UserRepository userRepository;

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "createUser", notes = "creates and returns a new user")
  public User createUser() {
    User user = new User();
    userRepository.save(user);

    return user;
  }

  @RequestMapping(
    value = "/{userId}",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ApiOperation(value = "getUser", notes = "gets and existing user")
  public User getUser(@PathVariable String userId) throws UserNotFound {
    User user = userRepository.findOne(userId);

    if (user == null) throw new UserNotFound("User '" + userId + "' could not be found");

    return user;
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
  @ApiOperation(value = "deleteUser", notes = "deletes a user")
  public String deleteUser(@PathVariable String userId) {
    userRepository.delete(userId);

    return "OK";
  }
}

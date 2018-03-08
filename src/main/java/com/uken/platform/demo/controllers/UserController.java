package com.uken.platform.demo.controllers;

import com.uken.platform.demo.exceptions.UserNotFound;
import com.uken.platform.demo.models.User;
import com.uken.platform.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/users")
public class UserController {

  @Autowired
  UserRepository userRepository;

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public User createUser() {
    User user = new User();
    userRepository.save(user);

    return user;
  }

  @RequestMapping(value = "/{userId}",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public User getUser(@PathVariable String userId) throws UserNotFound {
    User user = userRepository.findOne(userId);

    if (user == null)
      throw new UserNotFound("User '" + userId + "' could not be found");

    return user;
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
  public String deleteUser(@PathVariable String userId) {
    userRepository.delete(userId);

    return "OK";
  }
}

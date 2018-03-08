package com.uken.platform.demo.repositories;

import com.uken.platform.demo.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}

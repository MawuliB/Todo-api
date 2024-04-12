package com.api.myapi.repositories;

import com.api.myapi.domain.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}

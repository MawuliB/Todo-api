package com.api.myapi.repositories;

import com.api.myapi.entities.UserDB;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDB, Long> {
}

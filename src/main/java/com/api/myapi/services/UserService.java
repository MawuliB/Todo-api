package com.api.myapi.services;

import com.api.myapi.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    User createUser(User user);
    User getUserById(int id);
    User updateUser(User user);
    void deleteUser(int id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User getAllUsers();
}

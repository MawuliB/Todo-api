package com.api.myapi.services;

import com.api.myapi.domain.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    User updateUser(User user, Long id);
    void deleteUser(Long id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    List<User> getAllUsers();
}

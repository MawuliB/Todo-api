package com.api.myapi.controllers;

import com.api.myapi.domain.dto.UserDto;
import com.api.myapi.domain.entities.User;
import com.api.myapi.mappers.Mapper;
import com.api.myapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final Mapper<User, UserDto> userMapper;

    public UserController(UserService userService, Mapper<User, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("")
    public String Home(){
        return "Welcome To My User API";
    }

    @PostMapping("/create")
    public UserDto createUser(@RequestBody UserDto user){
        if (user.getUsername().isEmpty()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        }
        User userEntity = userMapper.mapFrom(user);
        User savedUser = userService.createUser(userEntity);
        return userMapper.mapTo(savedUser);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        if (this.userService.getUserById(id) == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userMapper.mapTo(this.userService.getUserById(id)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        List<User> users = this.userService.getAllUsers();
        return users.stream().map(userMapper::mapTo).toList();
    }

    @PutMapping("/update/{id}")
    public UserDto updateUserById(@PathVariable(name = "id") Long id, @RequestBody UserDto user) {
        if (this.userService.getUserById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d not found", id));
        }
        User userEntity = userMapper.mapFrom(user);
        return userMapper.mapTo(this.userService.updateUser(userEntity, id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable(name = "id") Long id) {
        if (this.userService.getUserById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d not found", id));
        }
        this.userService.deleteUser(id);
    }
}

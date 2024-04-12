package com.api.myapi.controllers;

import com.api.myapi.domain.dto.TodoDto;
import com.api.myapi.domain.dto.UserDto;
import com.api.myapi.domain.entities.Todo;
import com.api.myapi.domain.entities.User;
import com.api.myapi.mappers.Mapper;
import com.api.myapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

//    @GetMapping("/get/{id}")
//    public UserDto getUserById(@PathVariable(name = "id") Long id) {
//        if (this.userService.findById(id).isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d not found", id));
//        }
//        return this.userService.findById(id).get();
//    }
//
//    @GetMapping("/all")
//    public Iterable<UserDto> getAllUsers() {
//        return this.userService.findAll();
//    }
//
//    @PutMapping("/update/{id}")
//    public UserDto updateUserById(@PathVariable(name = "id") Long id, @RequestBody UserDto user) {
//        if (this.userService.findById(id).isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d not found", id));
//        }
//        user.setId(id);
//        return this.userService.save(user);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public void deleteUserById(@PathVariable(name = "id") Long id) {
//        if (this.userService.findById(id).isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d not found", id));
//        }
//        this.userService.deleteById(id);
//    }
}

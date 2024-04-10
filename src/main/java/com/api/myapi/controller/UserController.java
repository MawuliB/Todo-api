package com.api.myapi.controller;

import com.api.myapi.entities.UserDB;
import com.api.myapi.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public String Home(){
        return "Welcome To My User API";
    }

    @PostMapping("/create")
    public UserDB createUser(@RequestBody UserDB user){
        if (user.getUsername().isEmpty()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        }

        return this.userRepository.save(user);
    }

    @GetMapping("/get/{id}")
    public UserDB getUserById(@PathVariable(name = "id") Long id) {
        if (this.userRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d not found", id));
        }
        return this.userRepository.findById(id).get();
    }

    @GetMapping("/all")
    public Iterable<UserDB> getAllUsers() {
        return this.userRepository.findAll();
    }

    @PutMapping("/update/{id}")
    public UserDB updateUserById(@PathVariable(name = "id") Long id, @RequestBody UserDB user) {
        if (this.userRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d not found", id));
        }
        user.setId(id);
        return this.userRepository.save(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable(name = "id") Long id) {
        if (this.userRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d not found", id));
        }
        this.userRepository.deleteById(id);
    }
}

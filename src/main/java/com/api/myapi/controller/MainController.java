package com.api.myapi.controller;

import com.api.myapi.component.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api")
public class MainController {

    ArrayList<Todo> todoList = new ArrayList<Todo>();
    @GetMapping("")
    public String Home() {
        return "Welcome to my API!";
    }

    @PostMapping("/createtodo")
    public String createTodo(@RequestBody Todo todo) {
        Todo createdtodo = new Todo(todo.getTitle(), todo.getDescription(), todo.isDone());
        todoList.add(createdtodo);
        return createdtodo.toString();
    }

    @GetMapping("/gettodo/{id}")
    public String getTodo(@PathVariable int id) {
        if(id >= todoList.size())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
    return todoList.get(id).toString();
    }

    @GetMapping("/getalltodos")
    public String getAllTodos() {
        return todoList.toString();
    }

    @GetMapping("/error")
    public String error() {
        return "An error occurred!";
    }

}

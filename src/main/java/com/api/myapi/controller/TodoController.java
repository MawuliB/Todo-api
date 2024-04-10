package com.api.myapi.controller;

import com.api.myapi.entities.TodoDB;
import com.api.myapi.repositories.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/todos")
public class TodoController {
    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("all")
    public Iterable<TodoDB> getAllTodos() {
        return this.todoRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public Optional<TodoDB> getTodoById(@PathVariable("id") Integer id) {
        if(this.todoRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Todo with id %d not found", id));
        }
        return this.todoRepository.findById(id);
    }

    @PostMapping("/create")
    public TodoDB createTodo(@RequestBody TodoDB todo) {
        return this.todoRepository.save(todo);
    }

    @PutMapping("/update/{id}")
    public TodoDB updateTodoById(@PathVariable("id") Integer id, @RequestBody TodoDB todo) {
        try {
            if (this.todoRepository.findById(id).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Todo with id %d not found", id));
            }
            todo.setId(id);
            return this.todoRepository.save(todo);
        }catch (Exception e){
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTodoById(@PathVariable("id") Integer id) {
//        Optional<TodoDB> u = this.todoRepository.findById(id);
//        TodoDB t = u.get();
//        t.setDescription(t.getDescription());
        if (this.todoRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Todo with id %d not found", id));
        }
        this.todoRepository.deleteById(id);
    }

    @GetMapping("/done/{done}")
    public List<TodoDB> getTodosByDone(@PathVariable("done") boolean done) {
        return this.todoRepository.findByDone(done);
    }

    @GetMapping("/search")
    public List<TodoDB> searchTodos(@RequestParam(name = "title", required = false) String title, @RequestParam(name = "description", required = false) String description){
        if(title != null && description != null){
            return this.todoRepository.findByTitleContainsIgnoreCaseAndDescriptionContainsIgnoreCase(title, description);
        } else if (title != null ) {
            return this.todoRepository.findByTitleContainsIgnoreCase(title);
        } else if (description != null) {
            return this.todoRepository.findByDescriptionContainsIgnoreCase(description);
        }else {
            return new ArrayList<>();
        }
    }
}

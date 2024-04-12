package com.api.myapi.controllers;

import com.api.myapi.domain.dto.TodoDto;
import com.api.myapi.domain.entities.Todo;
import com.api.myapi.mappers.Mapper;
import com.api.myapi.repositories.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/todos")
public class TodoController {
    private final TodoRepository todoRepository;
    private final Mapper<Todo, TodoDto> todoMapper;

    public TodoController(TodoRepository todoRepository, Mapper<Todo, TodoDto> todoMapper) {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
    }

    @GetMapping("all")
    public Iterable<Todo> getAllTodos() {
        return this.todoRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public Optional<Todo> getTodoById(@PathVariable("id") Integer id) {
        if(this.todoRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Todo with id %d not found", id));
        }
        return this.todoRepository.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todo) {
        Todo todoEntity = todoMapper.mapFrom(todo);
        Todo savedTodo = this.todoRepository.save(todoEntity);
        return new ResponseEntity<>(todoMapper.mapTo(savedTodo), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public Todo updateTodoById(@PathVariable("id") Integer id, @RequestBody Todo todo) {
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
    public List<Todo> getTodosByDone(@PathVariable("done") boolean done) {
        return this.todoRepository.findByDone(done);
    }

    @GetMapping("/search")
    public List<Todo> searchTodos(@RequestParam(name = "title", required = false) String title, @RequestParam(name = "description", required = false) String description){
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

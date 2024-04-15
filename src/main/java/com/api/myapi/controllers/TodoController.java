package com.api.myapi.controllers;

import com.api.myapi.domain.dto.TodoDto;
import com.api.myapi.domain.entities.Todo;
import com.api.myapi.mappers.Mapper;
import com.api.myapi.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/todos")
public class TodoController {
    private final TodoService todoService;
    private final Mapper<Todo, TodoDto> todoMapper;

    public TodoController(TodoService todoService, Mapper<Todo, TodoDto> todoMapper) {
        this.todoService = todoService;
        this.todoMapper = todoMapper;
    }

    @GetMapping("/all")
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = this.todoService.getAllTodos();
        return todos.stream().map(todoMapper::mapTo).toList();
    }

    @GetMapping("/get/{id}")
    public Optional<TodoDto> getTodoById(@PathVariable("id") Integer id) {
        if(this.todoService.getTodoById(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Todo with id %d not found", id));
        }
        return Optional.ofNullable(todoMapper.mapTo(this.todoService.getTodoById(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todo) {
        Todo todoEntity = todoMapper.mapFrom(todo);
        Todo savedTodo = this.todoService.createTodo(todoEntity);
        return new ResponseEntity<>(todoMapper.mapTo(savedTodo), HttpStatus.CREATED);
    }

//    @PutMapping("/update/{id}")
//    public Todo updateTodoById(@PathVariable("id") Integer id, @RequestBody Todo todo) {
//        try {
//            if (this.todoService.findById(id).isEmpty()) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Todo with id %d not found", id));
//            }
//            todo.setId(id);
//            return this.todoService.save(todo);
//        }catch (Exception e){
//            return null;
//        }
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public void deleteTodoById(@PathVariable("id") Integer id) {
////        Optional<TodoDB> u = this.todoService.findById(id);
////        TodoDB t = u.get();
////        t.setDescription(t.getDescription());
//        if (this.todoService.findById(id).isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Todo with id %d not found", id));
//        }
//        this.todoService.deleteById(id);
//    }
//
//    @GetMapping("/done/{done}")
//    public List<Todo> getTodosByDone(@PathVariable("done") boolean done) {
//        return this.todoService.findByDone(done);
//    }
//
//    @GetMapping("/search")
//    public List<Todo> searchTodos(@RequestParam(name = "title", required = false) String title, @RequestParam(name = "description", required = false) String description){
//        if(title != null && description != null){
//            return this.todoService.findByTitleContainsIgnoreCaseAndDescriptionContainsIgnoreCase(title, description);
//        } else if (title != null ) {
//            return this.todoService.findByTitleContainsIgnoreCase(title);
//        } else if (description != null) {
//            return this.todoService.findByDescriptionContainsIgnoreCase(description);
//        }else {
//            return new ArrayList<>();
//        }
//    }
}

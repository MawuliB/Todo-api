package com.api.myapi.services;

import com.api.myapi.domain.entities.Todo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TodoService {
    Todo createTodo(Todo todo);
    Todo getTodoById(Long id);
    Todo updateTodo(Todo todo, Long id);
    void deleteTodoById(Long id);
    List<Todo> getAllTodos();
}

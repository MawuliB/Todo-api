package com.api.myapi.services;

import com.api.myapi.domain.entities.Todo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TodoService {
    Todo createTodo(Todo todo);
    Todo getTodoById(int id);
    Todo updateTodo(Todo todo);
    void deleteTodoById(int id);
    List<Todo> getAllTodos();
}

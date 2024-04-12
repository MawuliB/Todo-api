package com.api.myapi.services.impl;

import com.api.myapi.domain.entities.Todo;
import com.api.myapi.repositories.TodoRepository;
import com.api.myapi.services.TodoService;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Todo getTodoById(int id) {
        return null;
    }

    @Override
    public Todo updateTodo(Todo todo) {
        return null;
    }

    @Override
    public void deleteTodoById(int id) {

    }

    @Override
    public Todo getAllTodos() {
        return null;
    }

}

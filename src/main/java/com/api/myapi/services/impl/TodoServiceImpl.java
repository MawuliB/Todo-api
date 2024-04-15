package com.api.myapi.services.impl;

import com.api.myapi.domain.entities.Todo;
import com.api.myapi.domain.entities.User;
import com.api.myapi.repositories.TodoRepository;
import com.api.myapi.repositories.UserRepository;
import com.api.myapi.services.TodoService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository, EntityManager entityManager) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Todo createTodo(Todo todo) {
        if(!todo.getUser().getUsername().isEmpty()) {
            User user = userRepository.findByUsername(todo.getUser().getUsername());
            user.setPassword(null);
            user = entityManager.merge(user);
            todo.setUser(user);
            return todoRepository.save(todo);
        }else {
            throw new RuntimeException("Username is required");
        }
    }

    @Override
    public Todo getTodoById(Long id) {
        return todoRepository.findById(Math.toIntExact(id)).orElse(null);
    }

    @Override
    public Todo updateTodo(Todo todo, Long id) {
        todo.setId(Math.toIntExact(id));
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodoById(Long id) {
        todoRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public List<Todo> getAllTodos() {
        return StreamSupport.stream(todoRepository.findAll().spliterator(), false).toList();
    }

}

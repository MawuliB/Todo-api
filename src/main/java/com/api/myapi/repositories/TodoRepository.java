package com.api.myapi.repositories;

import com.api.myapi.entities.TodoDB;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<TodoDB, Integer> {
    List<TodoDB> findByDone(boolean done);
    List<TodoDB> findByTitleContainsIgnoreCaseAndDescriptionContainsIgnoreCase(String title, String description);
    List<TodoDB> findByTitleContainsIgnoreCase(String title);
    List<TodoDB> findByDescriptionContainsIgnoreCase(String description);

}

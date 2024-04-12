package com.api.myapi.repositories;

import com.api.myapi.domain.entities.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Integer> {
    List<Todo> findByDone(boolean done);
    List<Todo> findByTitleContainsIgnoreCaseAndDescriptionContainsIgnoreCase(String title, String description);
    List<Todo> findByTitleContainsIgnoreCase(String title);
    List<Todo> findByDescriptionContainsIgnoreCase(String description);

}

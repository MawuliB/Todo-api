package com.api.myapi.mappers.Impl;

import com.api.myapi.domain.dto.TodoDto;
import com.api.myapi.domain.entities.Todo;
import com.api.myapi.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TodoMapperImpl implements Mapper<Todo, TodoDto> {
    private final ModelMapper modelMapper;

    public TodoMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TodoDto mapTo(Todo todo) {
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public Todo mapFrom(TodoDto todoDto) {
        return modelMapper.map(todoDto, Todo.class);
    }
}

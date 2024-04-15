package com.api.myapi.controllers;

import com.api.myapi.TestDataUtility;
import com.api.myapi.domain.dto.TodoDto;
import com.api.myapi.domain.dto.UserDto;
import com.api.myapi.mappers.Impl.TodoMapperImpl;
import com.api.myapi.mappers.Impl.UserMapperImpl;
import com.api.myapi.services.TodoService;
import com.api.myapi.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {
    private final MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private final UserService userService;
    private final TodoService todoService;
    @Autowired
    private UserMapperImpl userMapperImpl;
    @Autowired
    private TodoMapperImpl todoMapperImpl;

    @Autowired
    public TodoControllerTest(MockMvc mockMvc, TodoService todoService, UserService userService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.todoService = todoService;
        this.userService = userService;
    }

    @Test
    public void testCreateTodo() throws Exception {
        UserDto userDto1 = TestDataUtility.createUserDto();
        userService.createUser(userMapperImpl.mapFrom(userDto1));
        TodoDto todoDto = TestDataUtility.createTodoDto(userService);
        todoService.createTodo(todoMapperImpl.mapFrom(todoDto));
        ObjectMapper objectMapper = new ObjectMapper();
        String todoJson = objectMapper.writeValueAsString(todoDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/todos/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoJson))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.title").value(todoDto.getTitle())
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.description").value(todoDto.getDescription())
                );


    }

    @Test
    public void testCreateTodoHTTPStatus200() throws Exception {
        UserDto userDto1 = TestDataUtility.createUserDto();
        userService.createUser(userMapperImpl.mapFrom(userDto1));
        TodoDto todoDto = TestDataUtility.createTodoDto(userService);
        todoService.createTodo(todoMapperImpl.mapFrom(todoDto));
        ObjectMapper objectMapper = new ObjectMapper();
        String todoJson = objectMapper.writeValueAsString(todoDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/todos/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoJson))
                .andExpect(
                        MockMvcResultMatchers.status().isCreated()
                );
    }

    @Test
    public void testGetAllTodosReturnsList() throws Exception {
        UserDto userDto1 = TestDataUtility.createUserDto();
        userService.createUser(userMapperImpl.mapFrom(userDto1));
        TodoDto todoDto = TestDataUtility.createTodoDto(userService);
        todoService.createTodo(todoMapperImpl.mapFrom(todoDto));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/todos/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$").isArray()
                );
    }
}

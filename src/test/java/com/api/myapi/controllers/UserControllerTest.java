package com.api.myapi.controllers;

import com.api.myapi.TestDataUtility;
import com.api.myapi.domain.dto.UserDto;
import com.api.myapi.domain.entities.User;
import com.api.myapi.mappers.Impl.UserMapperImpl;
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
public class UserControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    @Autowired
    private UserMapperImpl userMapperImpl;

    @Autowired
    public UserControllerTest(MockMvc mockMvc, UserService userService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.userService = userService;
    }


    @Test
    public void testCreateUser() throws Exception {
        UserDto userDto = TestDataUtility.createUserDto();
        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.username").value(userDto.getUsername())
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.email").value(userDto.getEmail())
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.password").value(userDto.getPassword())
                );
    }

    @Test
    public void testCreateUserHttpBadRequest() throws Exception {
        UserDto userDto = TestDataUtility.createUserDto();
        userDto.setUsername("");

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                );
    }

    @Test
    public void testCreateUserResponseOk () throws Exception {
        UserDto userDto = TestDataUtility.createUserDto();

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void testGetAllUsersReturnsList() throws Exception {
        UserDto userDto1 = TestDataUtility.createUserDto();
        userService.createUser(userMapperImpl.mapFrom(userDto1));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$").isArray()
                );
    }

    @Test
    public void testGetAllUsersReturnsListResponseOk() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );
    }

}

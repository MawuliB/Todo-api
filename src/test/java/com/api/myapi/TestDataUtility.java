package com.api.myapi;

import com.api.myapi.domain.dto.TodoDto;
import com.api.myapi.domain.dto.UserDto;
import com.api.myapi.domain.entities.User;
import com.api.myapi.mappers.Mapper;
import com.api.myapi.services.UserService;

public class TestDataUtility {

    public static UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setEmail("mawulibadassou5@gmail.com");
        userDto.setPassword("password");
        return userDto;
    }

    public static User getUserDto(UserService userService) {
        return userService.getAllUsers().get(0);
    }

    public static Mapper<User, UserDto> getUserMapper() {
        return new Mapper<User, UserDto>() {
            @Override
            public UserDto mapTo(User user) {
                UserDto userDto = new UserDto();
                userDto.setUsername(user.getUsername());
                userDto.setEmail(user.getEmail());
                userDto.setPassword(user.getPassword());
                return userDto;
            }

            @Override
            public User mapFrom(UserDto userDto) {
                User user = new User();
                user.setUsername(userDto.getUsername());
                user.setEmail(userDto.getEmail());
                user.setPassword(userDto.getPassword());
                return user;
            }
        };
    }

    public static TodoDto createTodoDto(UserService userService) {
        TodoDto todoDto = new TodoDto();
        todoDto.setUser(getUserMapper().mapTo(getUserDto(userService)));
        todoDto.setTitle("Test Todo");
        todoDto.setDescription("Test Todo Description");
        return todoDto;
    }
}

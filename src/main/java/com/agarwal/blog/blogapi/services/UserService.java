package com.agarwal.blog.blogapi.services;

import com.agarwal.blog.blogapi.entity.User;
import com.agarwal.blog.blogapi.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerUser(User userDto);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Long id);
    UserDto getUser(Long id);
    List<UserDto> getAllUsers();
    void deleteUser(Long id);
}

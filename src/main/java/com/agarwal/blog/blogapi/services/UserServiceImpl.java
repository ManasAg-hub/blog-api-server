package com.agarwal.blog.blogapi.services;

import com.agarwal.blog.blogapi.entity.Role;
import com.agarwal.blog.blogapi.entity.User;
import com.agarwal.blog.blogapi.exceptions.ResourceAlreadyExistsException;
import com.agarwal.blog.blogapi.exceptions.ResourceNotFoundException;
import com.agarwal.blog.blogapi.payloads.UserDto;
import com.agarwal.blog.blogapi.repositories.RoleRepository;
import com.agarwal.blog.blogapi.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(User userDto) {
        return null;
    }

    @Override
    public UserDto createUser(UserDto userDto) {


        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if(existingUser.isPresent()) {
            throw new ResourceAlreadyExistsException("User", "email", userDto.getEmail());
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER").orElseThrow(() -> new ResourceNotFoundException("Role", "name" + "ROLE_USER", 0L)));
        User user = convertToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return convertToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User savedUser = userRepository.save(user);

        return convertToUserDto(savedUser);
    }

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return convertToUserDto(user);

    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToUserDto).toList();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        userRepository.delete(user);

    }



    //method to convert UserDto to User
    private User convertToUser(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }

    //method to convert User to UserDto
    private UserDto convertToUserDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}

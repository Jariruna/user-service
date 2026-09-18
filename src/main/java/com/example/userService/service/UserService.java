package com.example.userService.service;

import com.example.userService.dto.UserRegistrationRequestDto;
import com.example.userService.dto.UserResponseDto;
import com.example.userService.model.User;

import java.util.Optional;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationRequestDto resquestDto);

    Optional<User> findByEmail(String email);
}

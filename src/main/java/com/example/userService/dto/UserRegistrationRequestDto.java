package com.example.userService.dto;

public record UserRegistrationRequestDto(
        String username,
        String email,
        String password
) {}
package com.example.userService.dto;

public record UserResponseDto(
        Long id,
        String username,
        String email
) {}

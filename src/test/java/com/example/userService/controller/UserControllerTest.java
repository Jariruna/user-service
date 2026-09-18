package com.example.userService.controller;

import com.example.userService.dto.UserRegistrationRequestDto;
import com.example.userService.dto.UserResponseDto;
import com.example.userService.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Debería retornar HTTP 201 Created y los datos del usuario al registrarse exitosamente")
    void shouldReturnCreated_WhenUserRegistrationIsSuccessful() throws Exception {
        // Given
        var requestDto = new UserRegistrationRequestDto("john_doe", "john@example.com", "securePassword123");
        var responseDto = new UserResponseDto(1L, "john_doe", "john@example.com");

        given(userService.registerUser(any(UserRegistrationRequestDto.class))).willReturn(responseDto);

        // When & Then
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    @DisplayName("Debería retornar HTTP 400 Bad Request cuando el servicio lanza IllegalArgumentException")
    void shouldReturnBadRequest_WhenEmailAlreadyExists() throws Exception {
        // Given
        var requestDto = new UserRegistrationRequestDto("john_doe", "john@example.com", "securePassword123");

        given(userService.registerUser(any(UserRegistrationRequestDto.class)))
                .willThrow(new IllegalArgumentException("El correo electrónico ya está registrado."));

        // When & Then
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("El correo electrónico ya está registrado."));
    }
}
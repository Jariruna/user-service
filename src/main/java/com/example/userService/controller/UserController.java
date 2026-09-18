package com.example.userService.controller;

import com.example.userService.dto.UserRegistrationRequestDto;
import com.example.userService.dto.UserResponseDto;
import com.example.userService.service.UserService;
import com.example.userService.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder; // Añadido para resolver el error de compilación

    // Inyección de dependencias por constructor
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRegistrationRequestDto requestDto) {
        UserResponseDto responseDto = userService.registerUser(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRegistrationRequestDto loginDto) {
        logger.info("-> Correo recibido del form: {}", loginDto.email());
        logger.info("-> Contraseña recibida del form: [PROTEGIDA]");

        Optional<User> userOpt = userService.findByEmail(loginDto.email());

        if (userOpt.isPresent()) {
            logger.info("-> Usuario encontrado en BD con ID: {}", userOpt.get().getId());
            boolean match = passwordEncoder.matches(loginDto.password(), userOpt.get().getPassword());
            logger.info("-> ¿Coincide la contraseña?: {}", match);

            if (match) {
                User user = userOpt.get();
                UserResponseDto responseDto = new UserResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                );
                return ResponseEntity.ok(responseDto);
            }
        } else {
            logger.info("-> El correo no arrojó ningún resultado en la base de datos.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Correo o contraseña incorrectos."));
    }
}
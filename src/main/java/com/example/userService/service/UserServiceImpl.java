package com.example.userService.service;

import com.example.userService.dto.UserRegistrationRequestDto;
import com.example.userService.dto.UserResponseDto;
import com.example.userService.model.User;
import com.example.userService.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto registerUser(UserRegistrationRequestDto requestDto) {
        // Regla de negocio: Validar si el correo ya existe
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        // Crear la entidad y encriptar la contraseña
        User user = new User();
        user.setUsername(requestDto.username());
        user.setEmail(requestDto.email());
        user.setPassword(passwordEncoder.encode(requestDto.password()));

        // Guardar en la base de datos
        User savedUser = userRepository.save(user);

        // Retornar el DTO de respuesta
        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
package com.example.userService.service;

import com.example.userService.dto.UserRegistrationRequestDto;
import com.example.userService.dto.UserResponseDto;
import com.example.userService.model.User;
import com.example.userService.repository.UserRepository;
import org.glassfish.jaxb.core.v2.runtime.IllegalAnnotationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private  UserServiceImpl userService;

    @Test
    @DisplayName("Debería registrar un usuario exitosamente cuando el correo no existe")
    void shouldRegisterUserSuccessfully_WhenEmailDoesNotExist() {

        var resquestDto = new UserRegistrationRequestDto("john_doe", "john@example.com", "securePassword123");

        given(userRepository.existsByEmail(resquestDto.email())).willReturn(false);
        given(passwordEncoder.encode(resquestDto.password())).willReturn("encodePassword123");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername(resquestDto.username());
        savedUser.setEmail(resquestDto.email());
        savedUser.setPassword("encoderPassword123");

        given(userRepository.save(any(User.class))).willReturn(savedUser);

        UserResponseDto responseDto = userService.registerUser(resquestDto);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.id()).isEqualTo(1L);
        assertThat(responseDto.username()).isEqualTo("john_doe");
        assertThat(responseDto.email()).isEqualTo("john@example.com");

        then(userRepository).should().existsByEmail("john@example.com");
        then(passwordEncoder).should().encode("securePassword123");
        then(userRepository).should().save((any(User.class)));

    }

    void shouldThrowException_WhenEmailAlreadyExists(){
        var requestDto = new UserRegistrationRequestDto("john_doe", "john@example.com", "securePassword123");

        given(userRepository.existsByEmail(requestDto.email())).willReturn(true);

        assertThatThrownBy(() -> userService.registerUser(requestDto))
                .isInstanceOf(IllegalAnnotationException.class)
                .hasMessage("El Correo electronico ya esta registrado.");

        then(userRepository).should().existsByEmail("john@example.com");
        then(passwordEncoder).shouldHaveNoInteractions();
        then(userRepository).should(never()).save(any(User.class));

    }
}

package com.nips.api.user.application.service;

import com.nips.api.user.application.dto.AuthCredentialsRequestDto;
import com.nips.api.user.application.dto.AuthCredentialsResponseDto;
import com.nips.api.user.application.dto.AuthRegisterRequestDto;
import com.nips.api.user.application.dto.PhoneDto;
import com.nips.api.user.domain.mapper.PhoneMapper;
import com.nips.api.user.domain.mapper.UserMapper;
import com.nips.api.user.domain.model.Phone;
import com.nips.api.user.domain.model.Role;
import com.nips.api.user.domain.model.User;
import com.nips.api.user.domain.repository.IPhoneRepository;
import com.nips.api.user.domain.repository.IRoleRepository;
import com.nips.api.user.domain.repository.IUserRepository;
import com.nips.api.user.infraestructure.config.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

class UserServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserSecurityService userSecurityService;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private IPhoneRepository phoneRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PhoneMapper phoneMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void  givenValidCredentials_whenLogin_thenSuccess() {
        // Arrange
        AuthCredentialsRequestDto requestDto = new AuthCredentialsRequestDto();
        requestDto.setEmail("user@example.com");
        requestDto.setPassword("password");
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
        when(userSecurityService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtUtils.createToken(any(UserDetails.class))).thenReturn("token");

        // Act
        ResponseEntity<?> response = userService.login(requestDto);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void givenInvalidCredentials_whenLogin_thenUnauthorized() {
        // Arrange
        AuthCredentialsRequestDto requestDto = new AuthCredentialsRequestDto();
        requestDto.setEmail("user@example.com");
        requestDto.setPassword("password");
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(BadCredentialsException.class);

        // Act
        ResponseEntity<?> response = userService.login(requestDto);

        // Assert
        assertEquals(UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void givenNewUser_whenRegister_thenSuccess() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        AuthRegisterRequestDto requestDto = new AuthRegisterRequestDto();
        requestDto.setEmail("newuser@example.com");
        requestDto.setPassword("Password");
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setNumber("1234567890");
        phoneDto.setCityCode("1");
        phoneDto.setCountryCode("57");
        requestDto.setPhones(List.of(phoneDto));
        requestDto.setName("New User");

        User user = new User();
        user.setId(1L);
        user.setEmail("newuser@example.com");
        user.setPassword("Password");
        user.setIsActive(true);
        Role role = new Role();
        role.setId(1L);
        user.setRoles(List.of(role));
        Phone phone = new Phone();
        phoneMapper.toModel(phoneDto);
        user.setPhones(List.of(phone));
        user.setCreatedAt(now);
        user.setModifiedAt(now);
        user.setLastLogin(now);
        user.setIsActive(true);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userMapper.frmRequestTo(any(AuthRegisterRequestDto.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtUtils.createToken(any(UserDetails.class))).thenReturn("token");

        // Act
        ResponseEntity<?> response = userService.register(requestDto);

        // Assert
        assertEquals(CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void givenExistingEmail_whenRegister_thenConflict() {
        // Arrange
        AuthRegisterRequestDto requestDto = new AuthRegisterRequestDto();
        requestDto.setEmail("existinguser@example.com");
        // Ajusta los demás campos necesarios para tu prueba aquí

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act
        ResponseEntity<?> response = userService.register(requestDto);

        // Assert
        assertEquals(CONFLICT, response.getStatusCode());
    }
}
package com.nips.api.user.application.service;

import com.nips.api.user.application.dto.AuthCredentialsRequestDto;
import com.nips.api.user.application.dto.AuthCredentialsResponseDto;
import com.nips.api.user.application.dto.RoleDto;
import com.nips.api.user.application.dto.UserDto;
import com.nips.api.user.domain.mapper.UserMapper;
import com.nips.api.user.domain.model.User;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserSecurityServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserSecurityService userSecurityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenExistingEmail_whenLoadUserByUsername_thenUserDetailsReturned() {
        // Given
        String email = "user@example.com";
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail(email);
        userDto.setPassword("password");
        // Setup roles as needed
        RoleDto roleDto = new RoleDto();
        roleDto.setName("USER");
        userDto.setRoles(List.of(roleDto));

        when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(new User()));
        when(userMapper.toDto(any())).thenReturn(userDto);

        // When
        UserDetails userDetails = userSecurityService.loadUserByUsername(email);

        // Then
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void givenNonExistentEmail_whenLoadUserByUsername_thenUsernameNotFoundException() {
        // Given
        String email = "nonexistent@example.com";
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userSecurityService.loadUserByUsername(email);
        });

        // Then
        assertEquals("User not found", exception.getMessage());
    }
}
package com.nips.api.user.application.service;

import com.nips.api.user.application.dto.AuthCredentialsRequestDto;
import com.nips.api.user.application.dto.AuthCredentialsResponseDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserSecurityService userSecurityService;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenLoginWithValidCredentials_thenReturnsJwt() {
        // Given
        AuthCredentialsRequestDto requestDto = new AuthCredentialsRequestDto();
        requestDto.setEmail("user@example.com");
        requestDto.setPassword("password");
        UserDetails mockUserDetails = org.mockito.Mockito.mock(UserDetails.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userSecurityService.loadUserByUsername("user@example.com")).thenReturn(mockUserDetails);
        when(jwtUtils.createToken(mockUserDetails)).thenReturn("mockJwtToken");

        // When
        ResponseEntity<AuthCredentialsResponseDto> response = userService.login(requestDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mockJwtToken", response.getBody().getToken());
    }

    @Test
    void whenLoginWithEmptyCredentials_thenReturnsBadRequest() {
        // Given
        AuthCredentialsRequestDto requestDto = new AuthCredentialsRequestDto();

        // When
        ResponseEntity<AuthCredentialsResponseDto> response = userService.login(requestDto);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void whenLoginWithInvalidCredentials_thenReturnsUnauthorized() {
        // Given
        AuthCredentialsRequestDto requestDto = new AuthCredentialsRequestDto();
        requestDto.setEmail("bad@email.com");
        requestDto.setPassword("badPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException(""));

        // When
        ResponseEntity<AuthCredentialsResponseDto> response = userService.login(requestDto);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
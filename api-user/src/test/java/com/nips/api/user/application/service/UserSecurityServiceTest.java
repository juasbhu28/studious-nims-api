package com.nips.api.user.application.service;

import com.nips.api.user.application.dto.RoleDto;
import com.nips.api.user.application.dto.UserDto;
import com.nips.api.user.application.mapper.UserDtoMapper;
import com.nips.api.user.domain.model.User;
import com.nips.api.user.domain.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserSecurityServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserSecurityService userSecurityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenLoadUserByUsername_thenUserDetailsReturned() {
        // Given
        String email = "user@example.com";
        UserDto mockUserDto = new UserDto();
        mockUserDto.setEmail(email);
        mockUserDto.setPassword("password");
        RoleDto roleDto = new RoleDto();
        roleDto.setName("USER");
        mockUserDto.setRoles(List.of(roleDto));

        when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(new User()));
        when(userDtoMapper.toDto(any(User.class))).thenReturn(mockUserDto);

        // When
        UserDetails userDetails = userSecurityService.loadUserByUsername(email);

        // Then
        assertEquals(email, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
    }

    @Test
    public void whenLoadUserByUsernameWithNonexistentEmail_thenUsernameNotFoundException() {
        // Given
        String email = "nonexistent@example.com";
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(UsernameNotFoundException.class, () -> userSecurityService.loadUserByUsername(email));
    }
}

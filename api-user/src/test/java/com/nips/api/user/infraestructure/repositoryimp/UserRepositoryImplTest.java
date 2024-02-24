package com.nips.api.user.infraestructure.repositoryimp;

import com.nips.api.user.domain.mapper.UserMapper;
import com.nips.api.user.domain.model.User;
import com.nips.api.user.infraestructure.persistence.entity.UserEntity;
import com.nips.api.user.infraestructure.persistence.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenEmail_whenGetUserByEmail_thenSuccess() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        UserEntity userEntity = new UserEntity();
        when(userJpaRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userMapper.toModel(any(UserEntity.class))).thenReturn(user);

        // Act
        Optional<User> result = userRepositoryImpl.getUserByEmail(email);

        // Assert
        verify(userJpaRepository).findByEmail(email);
        assertTrue(result.isPresent());
        assertEquals(result.get(), user);
    }

    @Test
    void givenEmail_whenExistsByEmail_thenSuccess() {
        // Arrange
        String email = "test@example.com";
        when(userJpaRepository.existsByEmail(email)).thenReturn(true);

        // Act
        boolean exists = userRepositoryImpl.existsByEmail(email);

        // Assert
        verify(userJpaRepository).existsByEmail(email);
        assertTrue(exists);
    }

    @Test
    void givenUser_whenSave_thenSuccess() {
        // Arrange
        User user = new User();
        UserEntity userEntity = new UserEntity();
        when(userMapper.toEntity(user)).thenReturn(userEntity);
        when(userJpaRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toModel(any(UserEntity.class))).thenReturn(user);

        // Act
        User savedUser = userRepositoryImpl.save(user);

        // Assert
        verify(userJpaRepository).save(userEntity);
        assertNotNull(savedUser);
    }

    @Test
    void givenUserId_whenUpdateLastLogin_thenSuccess() {
        // Arrange
        Long id = 1L;
        doNothing().when(userJpaRepository).updateLast(any(LocalDateTime.class), anyLong());

        // Act
        userRepositoryImpl.updateLastLogin(id);

        // Assert
        verify(userJpaRepository).updateLast(any(LocalDateTime.class), anyLong());
    }
}
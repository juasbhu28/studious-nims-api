package com.nips.api.user.infraestructure.repositoryImp;

import com.nips.api.user.domain.mapper.UserMapper;
import com.nips.api.user.domain.model.User;
import com.nips.api.user.infraestructure.persistence.entity.UserEntity;
import com.nips.api.user.infraestructure.persistence.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetUserByEmail_thenUserReturned() {
        // Given
        String email = "user@example.com";
        UserEntity userEntity = new UserEntity(); // Asume que UserEntity es la entidad JPA
        userEntity.setEmail(email);
        User userModel = new User(); // Asume que User es el modelo de dominio
        userModel.setEmail(email);

        when(userJpaRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userMapper.toModel(userEntity)).thenReturn(userModel);

        // When
        Optional<User> foundUser = userRepositoryImpl.getUserByEmail(email);

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
    }

    @Test
    public void whenGetUserByEmailWithNonexistentEmail_thenEmptyOptionalReturned() {
        // Given
        String email = "nonexistent@example.com";
        when(userJpaRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When
        Optional<User> foundUser = userRepositoryImpl.getUserByEmail(email);

        // Then
        assertTrue(foundUser.isEmpty());
    }

}
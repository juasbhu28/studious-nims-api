package com.nips.api.user.infraestructure.repositoryimp;

import com.nips.api.user.domain.mapper.RoleMapper;
import com.nips.api.user.infraestructure.persistence.entity.RoleEntity;
import com.nips.api.user.infraestructure.persistence.entity.UserEntity;
import com.nips.api.user.infraestructure.persistence.repository.RoleJpaRepository;
import com.nips.api.user.infraestructure.persistence.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class RoleRepositoryImplTest {

    @Mock
    private RoleJpaRepository roleJpaRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleRepositoryImpl roleRepositoryImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenValidUserIdAndRoleId_whenSaveRoleUsers_thenAssignRoleToUser() {
        // Given
        Long userId = 1L;
        Long roleId = 1L;
        UserEntity userEntity = new UserEntity();
        RoleEntity roleEntity = new RoleEntity();

        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(roleJpaRepository.findById(roleId)).thenReturn(Optional.of(roleEntity));

        // When
        roleRepositoryImpl.saveRoleUsers(userId, roleId);

        // Then
        verify(userJpaRepository, times(1)).save(any(UserEntity.class));
        assert(userEntity.getRoles().contains(roleEntity));
    }

    @Test
    void givenInvalidUserId_whenSaveRoleUsers_thenThrowEntityNotFoundExceptionForUser() {
        // Given
        Long userId = 1L;
        Long roleId = 1L;
        when(userJpaRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> roleRepositoryImpl.saveRoleUsers(userId, roleId));
    }

    @Test
    void givenInvalidRoleId_whenSaveRoleUsers_thenThrowEntityNotFoundExceptionForRole() {
        // Given
        Long userId = 1L;
        Long roleId = 1L;
        UserEntity userEntity = new UserEntity();
        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(roleJpaRepository.findById(roleId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> roleRepositoryImpl.saveRoleUsers(userId, roleId));
    }
}
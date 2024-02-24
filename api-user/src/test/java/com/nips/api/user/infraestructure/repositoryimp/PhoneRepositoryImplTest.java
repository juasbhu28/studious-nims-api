package com.nips.api.user.infraestructure.repositoryimp;

import com.nips.api.user.domain.mapper.PhoneMapper;
import com.nips.api.user.domain.model.Phone;
import com.nips.api.user.infraestructure.persistence.entity.PhoneEntity;
import com.nips.api.user.infraestructure.persistence.entity.UserEntity;
import com.nips.api.user.infraestructure.persistence.repository.PhoneJpaRepository;
import com.nips.api.user.infraestructure.persistence.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PhoneRepositoryImplTest {

    @Mock
    private PhoneJpaRepository phoneJpaRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private PhoneMapper phoneMapper;

    @InjectMocks
    private PhoneRepositoryImpl phoneRepositoryImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenValidUserIdAndPhones_whenSavePhones_thenSavePhones() {

        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        List<Phone> phones = List.of(new Phone());
        List<PhoneEntity> phoneEntities = List.of(new PhoneEntity());

        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(phoneMapper.toEntityList(phones)).thenReturn(phoneEntities);

        phoneRepositoryImpl.savePhones(userId, phones);

        verify(userJpaRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void givenInvalidUserId_whenSavePhones_thenThrowEntityNotFoundException() {

        Long userId = 1L;
        when(userJpaRepository.findById(userId)).thenReturn(Optional.empty());

         assertThrows(EntityNotFoundException.class, () -> phoneRepositoryImpl.savePhones(userId, List.of(new Phone())));
    }
}
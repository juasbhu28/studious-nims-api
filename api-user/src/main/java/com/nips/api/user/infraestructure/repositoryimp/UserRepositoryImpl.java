package com.nips.api.user.infraestructure.repositoryimp;

import com.nips.api.user.domain.mapper.UserMapper;
import com.nips.api.user.domain.repository.IUserRepository;
import com.nips.api.user.domain.model.User;
import com.nips.api.user.infraestructure.persistence.repository.UserJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(userMapper::toModel);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public User save(User entity) {
        return userMapper.toModel(userJpaRepository.save(userMapper.toEntity(entity)));
    }

    @Override
    public void updateLastLogin(Long id) {
        userJpaRepository.updateLast(LocalDateTime.now(), id);
    }
}

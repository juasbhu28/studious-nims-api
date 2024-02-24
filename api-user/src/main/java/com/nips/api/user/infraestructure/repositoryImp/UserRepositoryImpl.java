package com.nips.api.user.infraestructure.repositoryImp;

import com.nips.api.user.domain.mapper.UserMapper;
import com.nips.api.user.domain.repository.IUserRepository;
import com.nips.api.user.domain.model.User;
import com.nips.api.user.infraestructure.persistence.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    @Override
    public User save(User entity) {
        return userMapper.toModel(userJpaRepository.save(userMapper.toEntity(entity)));
    }
}

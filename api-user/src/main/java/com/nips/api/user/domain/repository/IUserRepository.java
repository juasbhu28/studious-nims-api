package com.nips.api.user.domain.repository;


import com.nips.api.user.domain.model.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> getUserByEmail(String email);

    boolean existsByEmail(String email);

    User save(User entity);
}

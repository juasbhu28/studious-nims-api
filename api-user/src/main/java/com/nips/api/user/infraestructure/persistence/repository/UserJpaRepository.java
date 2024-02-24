package com.nips.api.user.infraestructure.persistence.repository;

import com.nips.api.user.infraestructure.persistence.entity.UserEntity;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}

package com.nips.api.user.infraestructure.persistence.repository;

import com.nips.api.user.infraestructure.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long>{

}

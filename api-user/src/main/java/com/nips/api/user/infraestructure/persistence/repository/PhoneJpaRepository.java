package com.nips.api.user.infraestructure.persistence.repository;

import com.nips.api.user.infraestructure.persistence.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneJpaRepository  extends JpaRepository<PhoneEntity, Long>{
}

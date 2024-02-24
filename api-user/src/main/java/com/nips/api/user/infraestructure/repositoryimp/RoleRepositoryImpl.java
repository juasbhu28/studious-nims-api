package com.nips.api.user.infraestructure.repositoryimp;

import com.nips.api.user.domain.mapper.RoleMapper;
import com.nips.api.user.domain.model.Role;
import com.nips.api.user.domain.repository.IRoleRepository;
import com.nips.api.user.infraestructure.persistence.entity.RoleEntity;
import com.nips.api.user.infraestructure.persistence.entity.UserEntity;
import com.nips.api.user.infraestructure.persistence.repository.RoleJpaRepository;
import com.nips.api.user.infraestructure.persistence.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class RoleRepositoryImpl implements IRoleRepository {

    @Autowired
    private RoleJpaRepository roleJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional
    public void saveRoleUsers(Long userId, Long rolId) {
        UserEntity user = userJpaRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        RoleEntity rol = roleJpaRepository.findById(rolId).orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
        user.setRoles(new ArrayList<>());
        user.getRoles().add(rol);
        userJpaRepository.save(user);
    }
}

package com.nips.api.user.infraestructure.repositoryimp;

import com.nips.api.user.domain.mapper.PhoneMapper;
import com.nips.api.user.domain.model.Phone;
import com.nips.api.user.domain.repository.IPhoneRepository;
import com.nips.api.user.infraestructure.persistence.entity.PhoneEntity;
import com.nips.api.user.infraestructure.persistence.entity.UserEntity;
import com.nips.api.user.infraestructure.persistence.repository.UserJpaRepository;
import com.nips.api.user.infraestructure.persistence.repository.PhoneJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PhoneRepositoryImpl extends IPhoneRepository {

    @Autowired
    private PhoneJpaRepository phoneJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PhoneMapper phoneMapper;

    @Override
    @Transactional
    public void savePhones(Long userId, List<Phone> phones) {
        UserEntity user = userJpaRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        List<PhoneEntity> phoneEntities = phoneMapper.toEntityList(phones);
        phoneEntities.forEach(phone -> phone.setUser(user));
        user.setPhones(new ArrayList<>());
        user.setPhones(phoneEntities);
        userJpaRepository.save(user);
    }
}

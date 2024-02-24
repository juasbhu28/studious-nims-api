package com.nips.api.user.domain.repository;

import com.nips.api.user.domain.model.Phone;
import jakarta.transaction.Transactional;

import java.util.List;

public abstract class IPhoneRepository {

    @Transactional
    public abstract void savePhones(Long userId, List<Phone> phones);
}

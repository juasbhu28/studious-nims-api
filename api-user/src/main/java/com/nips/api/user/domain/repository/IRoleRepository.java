package com.nips.api.user.domain.repository;

import jakarta.transaction.Transactional;

public interface IRoleRepository {
    @Transactional
    public abstract void saveRoleUsers(Long usuarioId, Long rolId);
}

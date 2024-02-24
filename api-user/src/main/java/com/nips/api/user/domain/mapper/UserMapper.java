package com.nips.api.user.domain.mapper;

import com.nips.api.user.domain.model.User;
import com.nips.api.user.infraestructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping( target = "id", source = "id")
    @Mapping( target = "name", source = "name")
    @Mapping( target = "email", source = "email")
    @Mapping( target = "password", source = "password")
    @Mapping( target = "phones", source = "phones")
    @Mapping( target = "Roles", source = "roles")
    User toModel(UserEntity userEntity);

}

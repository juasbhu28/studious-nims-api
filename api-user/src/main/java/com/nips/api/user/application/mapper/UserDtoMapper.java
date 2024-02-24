package com.nips.api.user.application.mapper;

import com.nips.api.user.application.dto.RoleDto;
import com.nips.api.user.application.dto.UserDto;
import com.nips.api.user.domain.mapper.RoleMapper;
import com.nips.api.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PhoneDtoMapper.class, RoleDtoMapper.class})
public interface UserDtoMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "isActive", source = "isActive")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "modifiedAt", source = "modifiedAt")
    @Mapping(target = "lastLogin", source = "lastLogin")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "phones", source = "phones")
    UserDto toDto(User user);

}

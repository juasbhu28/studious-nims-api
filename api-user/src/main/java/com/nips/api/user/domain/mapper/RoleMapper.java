package com.nips.api.user.domain.mapper;

import com.nips.api.user.application.dto.RoleDto;
import com.nips.api.user.domain.model.Role;
import com.nips.api.user.infraestructure.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RoleDto toDto(Role role);
    List<RoleDto> toDtoList(List<Role> roles);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RoleEntity toEntity(Role role);
    Role toModel(RoleEntity roleEntity);
}

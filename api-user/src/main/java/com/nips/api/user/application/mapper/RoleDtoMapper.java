package com.nips.api.user.application.mapper;

import com.nips.api.user.application.dto.RoleDto;
import com.nips.api.user.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleDtoMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RoleDto toDto(Role role);
    List<RoleDto> toDtoList(List<Role> roles);
}

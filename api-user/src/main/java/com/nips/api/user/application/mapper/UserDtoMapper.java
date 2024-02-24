package com.nips.api.user.application.mapper;

import com.nips.api.user.application.dto.AuthRegisterRequestDto;
import com.nips.api.user.application.dto.AuthRegisterResponseDto;
import com.nips.api.user.application.dto.RoleDto;
import com.nips.api.user.application.dto.UserDto;
import com.nips.api.user.domain.mapper.RoleMapper;
import com.nips.api.user.domain.model.Role;
import com.nips.api.user.domain.model.User;
import org.mapstruct.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "phones", source = "phones")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    User toEntity(AuthRegisterRequestDto authRegisterRequestDto);

    @AfterMapping
    default void setDefaultRole(@MappingTarget User user) {
        Role role = new Role();
        role.setId(1L);
        user.setRoles(List.of(role));
    }

    @Named("encodePassword")
    default String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}

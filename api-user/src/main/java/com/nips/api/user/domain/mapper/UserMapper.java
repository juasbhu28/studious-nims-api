package com.nips.api.user.domain.mapper;

import com.nips.api.user.application.dto.AuthRegisterRequestDto;
import com.nips.api.user.application.dto.UserDto;
import com.nips.api.user.domain.model.Role;
import com.nips.api.user.domain.model.User;
import com.nips.api.user.infraestructure.persistence.entity.UserEntity;
import org.mapstruct.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PhoneMapper.class, RoleMapper.class})
public interface UserMapper {

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
    @Mapping(target = "phones", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    User frmRequestTo(AuthRegisterRequestDto authRegisterRequestDto);

    @AfterMapping
    default void setDefaultRole(@MappingTarget User user) {
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        user.setRoles(List.of(role));
    }

    @Named("encodePassword")
    default String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "isActive", source = "isActive")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "modifiedAt", source = "modifiedAt")
    @Mapping(target = "lastLogin", source = "lastLogin")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "phones", ignore = true)
    UserEntity toEntity(User user);
    User toModel(UserEntity userEntity);

}

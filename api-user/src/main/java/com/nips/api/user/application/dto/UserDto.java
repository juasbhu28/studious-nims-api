package com.nips.api.user.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String token;
    private String created;
    private String modified;
    private String lastLogin;
    private boolean isActive;
    private List<RoleDto> roles;
    private List<PhoneDto> phones;

}

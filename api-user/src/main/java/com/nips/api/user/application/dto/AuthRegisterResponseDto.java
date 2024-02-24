package com.nips.api.user.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRegisterResponseDto {
    private String id;
    private String name;
    private String email;
    private String password;
    private String created;
    private String modified;
    private String lastLogin;
    private String token;
    private boolean isActive;
}

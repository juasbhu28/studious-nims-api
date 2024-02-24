package com.nips.api.user.domain.model;

import com.nips.api.user.infraestructure.persistence.entity.PhoneEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String created;
    private String modified;
    private String lastLogin;
    private List<Phone> phones;
    private List<Role> roles;

}

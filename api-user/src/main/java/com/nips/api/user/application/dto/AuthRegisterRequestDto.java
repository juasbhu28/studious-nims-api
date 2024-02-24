package com.nips.api.user.application.dto;

import com.nips.api.user.application.dto.PhoneDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthRegisterRequestDto {

    @Pattern(regexp = "^[a-zA-Z]*$", message = "El nombre debe ser alfabético")
    private String name;

    @Email(message = "El correo electrónico debe tener un formato válido")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "La contraseña debe ser alfanumérica")
    @Pattern(regexp = "^(?=.*[a-z])", message = "La contraseña debe tener al menos una letra minúscula")
    private String password;

    List<PhoneDto> phones;
}

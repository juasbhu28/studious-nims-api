package com.nips.api.user.application.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneDto {

    @Pattern(regexp = "^[0-9]*$", message = "El número de teléfono debe ser numérico")
    private String number;

    @Pattern(regexp = "^[0-9]*$", message = "El código de área debe ser numérico")
    private String cityCode;

    @Pattern(regexp = "^[0-9]*$", message = "El código de país debe ser numérico")
    private String countryCode;

}

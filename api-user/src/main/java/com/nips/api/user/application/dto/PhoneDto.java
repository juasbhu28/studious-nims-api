package com.nips.api.user.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneDto {
    private Long id;
    private String number;
    private String cityCode;
    private String countryCode;

}

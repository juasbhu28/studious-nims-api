package com.nips.api.user.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Phone {

    private Long id;
    private String number;
    private String cityCode;
    private String countryCode;

}

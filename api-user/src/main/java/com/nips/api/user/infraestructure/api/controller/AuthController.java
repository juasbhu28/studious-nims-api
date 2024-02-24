package com.nips.api.user.infraestructure.api.controller;


import com.nips.api.user.application.service.UserSecurityService;
import com.nips.api.user.application.dto.AuthCredentialsRequestDto;
import com.nips.api.user.application.dto.AuthCredentialsResponseDto;
import com.nips.api.user.common.RouteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ch.qos.logback.core.util.OptionHelper.isNullOrEmpty;

@RestController
@RequestMapping(RouteMapping.AUTH_API_ROOT)
public class AuthController {

    @Autowired
    private UserSecurityService userSecurityService;

    @PostMapping(value = RouteMapping.LOGIN_API, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthCredentialsResponseDto> login(@RequestBody AuthCredentialsRequestDto authRequestDto) {
        return userSecurityService.login(authRequestDto);
    }


}


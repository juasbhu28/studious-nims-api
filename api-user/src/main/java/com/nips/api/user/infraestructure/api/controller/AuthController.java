package com.nips.api.user.infraestructure.api.controller;


import com.nips.api.user.application.dto.AuthCredentialsRequestDto;
import com.nips.api.user.application.dto.AuthCredentialsResponseDto;
import com.nips.api.user.application.service.UserService;
import com.nips.api.user.common.RouteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(RouteMapping.AUTH_API_ROOT)
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = RouteMapping.LOGIN_API, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthCredentialsResponseDto> login(@RequestBody AuthCredentialsRequestDto authRequestDto) {
        return userService.login(authRequestDto);
    }


}


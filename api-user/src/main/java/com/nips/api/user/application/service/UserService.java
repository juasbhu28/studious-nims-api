package com.nips.api.user.application.service;

import com.nips.api.user.application.dto.AuthCredentialsRequestDto;
import com.nips.api.user.application.dto.AuthCredentialsResponseDto;
import com.nips.api.user.application.mapper.UserDtoMapper;
import com.nips.api.user.domain.repository.IUserRepository;
import com.nips.api.user.infraestructure.config.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static ch.qos.logback.core.util.OptionHelper.isNullOrEmpty;

@Service
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserSecurityService userSecurityService;

    @Autowired
    private JwtUtils jwtUtils;

    public UserService(AuthenticationManager authenticationManager, UserSecurityService userSecurityService) {
        this.authenticationManager = authenticationManager;
        this.userSecurityService = userSecurityService;
    }

    public ResponseEntity<AuthCredentialsResponseDto> login(AuthCredentialsRequestDto authRequestDto) {
        try {
            if (isNullOrEmpty(authRequestDto.getEmail())  || isNullOrEmpty(authRequestDto.getPassword())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            String jwt = creatingJwt(authRequestDto);
            return new ResponseEntity<>(new AuthCredentialsResponseDto(jwt), HttpStatus.OK);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private String creatingJwt(AuthCredentialsRequestDto authRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequestDto.getEmail().trim(), authRequestDto.getPassword().trim()));

        UserDetails userDetails = userSecurityService.loadUserByUsername(authRequestDto.getEmail());

        return jwtUtils.createToken(userDetails);
    }
}

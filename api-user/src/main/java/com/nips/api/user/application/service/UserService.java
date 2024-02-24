package com.nips.api.user.application.service;

import com.nips.api.user.application.dto.AuthCredentialsRequestDto;
import com.nips.api.user.application.dto.AuthCredentialsResponseDto;
import com.nips.api.user.application.dto.AuthRegisterRequestDto;
import com.nips.api.user.application.dto.AuthRegisterResponseDto;
import com.nips.api.user.application.mapper.UserDtoMapper;
import com.nips.api.user.domain.model.User;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserDtoMapper userDtoMapper;

    public ResponseEntity<AuthCredentialsResponseDto> login(AuthCredentialsRequestDto authRequestDto) {
        try {
            String jwt = creatingJwt(authRequestDto);
            AuthCredentialsResponseDto responseDto = new AuthCredentialsResponseDto();
            responseDto.setToken(jwt);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
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

    public ResponseEntity<AuthRegisterResponseDto> register(AuthRegisterRequestDto authRequestDto) {
        if( userRepository.existsByEmail(authRequestDto.getEmail()) ) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        User user = userRepository.save(userDtoMapper.toEntity(authRequestDto));
        AuthRegisterResponseDto responseDto = new AuthRegisterResponseDto();
        responseDto.setId(user.getId().toString());
        responseDto.setEmail(user.getEmail());
        responseDto.setName(user.getName());
        responseDto.setActive(user.getIsActive());
        responseDto.setCreated(user.getCreatedAt().toString());
        responseDto.setModified(user.getModifiedAt().toString());
        responseDto.setPassword(user.getPassword());
        responseDto.setToken(jwtUtils.createToken(userSecurityService.loadUserByUsername(user.getEmail())));

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}

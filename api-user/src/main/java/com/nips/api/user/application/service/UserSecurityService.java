package com.nips.api.user.application.service;

import com.nips.api.user.application.dto.AuthCredentialsRequestDto;
import com.nips.api.user.application.dto.AuthCredentialsResponseDto;
import com.nips.api.user.application.dto.RoleDto;
import com.nips.api.user.application.dto.UserDto;
import com.nips.api.user.application.mapper.UserDtoMapper;
import com.nips.api.user.domain.repository.IUserRepository;
import com.nips.api.user.infraestructure.config.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static ch.qos.logback.core.util.OptionHelper.isNullOrEmpty;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDto userDto = userRepository.getUserByEmail(email)
                .map(userDtoMapper::toDto)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String[] roles = userDto.getRoles().stream().map(RoleDto::getName).toArray(String[]::new);

        return User.builder()
                .username(userDto.getEmail())
                .password(userDto.getPassword())
                .roles(roles)
                .build();
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

        UserDetails userDetails = loadUserByUsername(authRequestDto.getEmail());

        return jwtUtils.createToken(userDetails);
    }

}
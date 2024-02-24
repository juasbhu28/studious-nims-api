package com.nips.api.user.application.service;

import com.nips.api.user.application.dto.AuthCredentialsRequestDto;
import com.nips.api.user.application.dto.AuthCredentialsResponseDto;
import com.nips.api.user.application.dto.AuthRegisterRequestDto;
import com.nips.api.user.application.dto.AuthRegisterResponseDto;
import com.nips.api.user.domain.mapper.PhoneMapper;
import com.nips.api.user.domain.mapper.UserMapper;
import com.nips.api.user.domain.model.User;
import com.nips.api.user.domain.repository.IPhoneRepository;
import com.nips.api.user.domain.repository.IRoleRepository;
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
    private IRoleRepository roleRepository;

    @Autowired
    private IPhoneRepository phoneRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PhoneMapper phoneMapper;

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
                authRequestDto.getEmail(), authRequestDto.getPassword()));

        UserDetails userDetails = userSecurityService.loadUserByUsername(authRequestDto.getEmail());

        return jwtUtils.createToken(userDetails);
    }

    public ResponseEntity<AuthRegisterResponseDto> register(AuthRegisterRequestDto authRequestDto) {
        if( userRepository.existsByEmail(authRequestDto.getEmail()) ) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        User user = userMapper.frmRequestTo(authRequestDto);
        user.setIsActive(true);
        user = userRepository.save(user);
        roleRepository.saveRoleUsers(user.getId(), user.getRoles().get(0).getId());
        phoneRepository.savePhones(user.getId(), phoneMapper.toModelList(authRequestDto.getPhones()));

        AuthRegisterResponseDto responseDto = new AuthRegisterResponseDto();
        responseDto.setId(user.getId().toString());
        responseDto.setEmail(user.getEmail());
        responseDto.setName(user.getName());
        responseDto.setActive(user.getIsActive());
        responseDto.setCreated(user.getCreatedAt().toString());
        responseDto.setModified(user.getModifiedAt().toString());
        responseDto.setLastLogin(user.getLastLogin().toString());
        responseDto.setPassword(user.getPassword());
        responseDto.setToken(jwtUtils.createToken(userSecurityService.loadUserByUsername(user.getEmail())));

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}

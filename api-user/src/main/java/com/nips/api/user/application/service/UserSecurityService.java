package com.nips.api.user.application.service;

import com.nips.api.user.application.dto.RoleDto;
import com.nips.api.user.application.dto.UserDto;
import com.nips.api.user.application.mapper.UserDtoMapper;
import com.nips.api.user.domain.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


}
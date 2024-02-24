package com.nips.api.user.application.service;

import com.nips.api.user.application.dto.RoleDto;
import com.nips.api.user.application.dto.UserDto;
import com.nips.api.user.domain.mapper.UserMapper;
import com.nips.api.user.domain.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDto userDto = userRepository.getUserByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userRepository.updateLastLogin(userDto.getId());
        
        String[] roles = userDto.getRoles().stream().map(RoleDto::getName).toArray(String[]::new);

        return User.builder()
                .username(userDto.getEmail())
                .password(userDto.getPassword())
                .roles(roles)
                .build();
    }


}
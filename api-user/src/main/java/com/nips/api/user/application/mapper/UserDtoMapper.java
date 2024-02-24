package com.nips.api.user.application.mapper;

import com.nips.api.user.application.dto.UserDto;
import com.nips.api.user.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDto toDto(User user);

}

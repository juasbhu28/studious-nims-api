package com.nips.api.user.application.mapper;

import com.nips.api.user.application.dto.PhoneDto;
import com.nips.api.user.domain.model.Phone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhoneDtoMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "cityCode", source = "cityCode")
    @Mapping(target = "countryCode", source = "countryCode")
    PhoneDto toDto(Phone phone);

    List<PhoneDto> toDtoList(List<Phone> phones);
}

package com.nips.api.user.domain.mapper;

import com.nips.api.user.application.dto.PhoneDto;
import com.nips.api.user.domain.model.Phone;
import com.nips.api.user.infraestructure.persistence.entity.PhoneEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    @Mapping(target = "number", source = "number")
    @Mapping(target = "cityCode", source = "cityCode")
    @Mapping(target = "countryCode", source = "countryCode")
    PhoneDto toDto(Phone phone);
    List<PhoneDto> toDtoList(List<Phone> phones);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    Phone toModel(PhoneDto phoneDto);
    List<Phone> toModelList(List<PhoneDto> phoneDtos);

    @Mapping(target = "number", source = "number")
    @Mapping(target = "cityCode", source = "cityCode")
    @Mapping(target = "countryCode", source = "countryCode")
    @Mapping(target = "user", ignore = true)
    PhoneEntity toEntity(Phone phone);
    List<PhoneEntity> toEntityList(List<Phone> phones);

}

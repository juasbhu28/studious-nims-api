package com.nips.api.user.domain.mapper;

import com.nips.api.user.domain.model.Phone;
import com.nips.api.user.infraestructure.persistence.entity.PhoneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "cityCode", source = "cityCode")
    @Mapping(target = "countryCode", source = "countryCode")
    Phone toModel(PhoneEntity phoneDto);
    List<Phone> toModelList(List<PhoneEntity> phoneDtos);
}

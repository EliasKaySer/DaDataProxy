package service.proxy.models.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import service.proxy.models.entityes.Address;
import service.proxy.models.transports.AddressDataDto;
import service.proxy.models.transports.AddressDto;

@Mapper(componentModel = "spring")
public abstract class AddressMaper {

    @Mappings({
            @Mapping(target = "value", expression = "java(addressDto.setValue())"),
            @Mapping(source = "postalcode", target = "data.postal_code"),
            @Mapping(source = "region", target = "data.region"),
            @Mapping(source = "city", target = "data.city"),
            @Mapping(source = "settlement", target = "data.settlement"),
            @Mapping(source = "street", target = "data.street"),
            @Mapping(source = "house", target = "data.house"),
    })
    public abstract AddressDto toDto(Address entity);

    @Mappings({
            @Mapping(source = "postal_code", target = "postalcode"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "requests", ignore = true)
    })
    public abstract Address toEntity(AddressDataDto dto);


}

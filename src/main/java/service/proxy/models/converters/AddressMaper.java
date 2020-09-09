package service.proxy.models.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import service.proxy.models.entityes.Address;
import service.proxy.models.transports.AddressDataDto;

@Mapper(componentModel = "spring")
public abstract class AddressMaper {

    @Mappings({
            @Mapping(source = "postalcode", target = "postal_code")
    })
    public abstract AddressDataDto toDto (Address entity);

    @Mappings({
            @Mapping(source = "postal_code", target = "postalcode"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "requests", ignore = true)
    })
    public abstract Address toEntity (AddressDataDto dto);



}

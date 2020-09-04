package service.proxy.addresses.model.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import service.proxy.addresses.model.entity.Address;
import service.proxy.dadata.model.transport.clean.AddressCleanDTO;
import service.proxy.dadata.model.transport.clean.AddressCleanDataDto;

@Mapper(componentModel = "spring")
public abstract class AddressMaper {

    @Mappings({
            @Mapping(target = "parent", ignore = true),
            @Mapping(source = "guid", target = "referenceId")
    })
    public abstract Address toEntity(AddressCleanDTO entity);
}

package service.proxy.models.converters;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import service.proxy.models.entityes.Request;

@Mapper(componentModel = "spring")
public abstract class RequestMaper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "count", ignore = true),
            @Mapping(target = "responses", ignore = true),
            @Mapping(target = "date", ignore = true)
    })
    public abstract Request newEntity(String query);

}

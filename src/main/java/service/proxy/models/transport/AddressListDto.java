package service.proxy.models.transport;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel(
        value = "Адресы",
        description = "Сущность ответа от сервиса DaData")
@Data
public class AddressListDto {
    private List<AddressDto> suggestions = new ArrayList<>();
}

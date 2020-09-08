package service.proxy.models.transports.dadata;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import service.proxy.models.transports.AddressDto;

import java.util.ArrayList;
import java.util.List;

@ApiModel(
        value = "Ответ",
        description = "Сущность ответа от сервиса DaData")
@Data
public class DaDataResponseDto {
    private List<AddressDto> suggestions = new ArrayList<>();
}

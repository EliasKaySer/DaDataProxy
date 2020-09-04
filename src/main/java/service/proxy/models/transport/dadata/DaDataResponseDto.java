package service.proxy.models.transport.dadata;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import service.proxy.models.transport.AddressDto;

import java.util.ArrayList;
import java.util.List;

@ApiModel(
        value = "Ответ",
        description = "Сущность ответа от сервиса DaData")
@Data
public class DaDataResponseDto {
    private List<AddressDto> suggestions = new ArrayList<>();
}

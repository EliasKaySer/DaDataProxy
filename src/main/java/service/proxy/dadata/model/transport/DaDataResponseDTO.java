package service.proxy.dadata.model.transport;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import service.proxy.dadata.model.transport.clean.AddressCleanDTO;

import java.util.List;

@ApiModel(
        value = "Адресы",
        description = "Сущьность ответа от сервиса DaData")
@Data
public class DaDataResponseDTO {
    private List<AddressCleanDTO> suggestions;
//    private List<AddressDTO> suggestions;
}

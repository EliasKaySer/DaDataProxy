package service.proxy.dadata.model.transport.clean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import service.proxy.dadata.model.transport.AddressDataDto;

@ApiModel(
        value = "Адрес",
        description = "Сущьность адреса из ответа DaData")
@Data
public class AddressCleanDTO {

    @ApiModelProperty(value = "Адрес одной строкой (как показывается в списке подсказок)")
    private String value;

    @ApiModelProperty(value = "Данные адреса")
    private AddressCleanDataDto data;

}

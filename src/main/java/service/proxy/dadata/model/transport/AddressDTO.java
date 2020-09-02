package service.proxy.dadata.model.transport;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(
        value = "Адрес",
        description = "Сущьность адреса из ответа DaData")
@Data
public class AddressDTO {

    @ApiModelProperty(value = "Адрес одной строкой (как показывается в списке подсказок)")
    private String value;

    @ApiModelProperty(value = "Адрес одной строкой (полный, с индексом)")
    private String unrestricted_value;

    @ApiModelProperty(value = "Данные адреса")
    private AddressDataDto data;

}

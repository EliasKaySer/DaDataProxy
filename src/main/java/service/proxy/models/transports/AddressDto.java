package service.proxy.models.transports;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(
        value = "Адрес",
        description = "Сущность адреса из ответа DaData")
@Data
public class AddressDto {

    @ApiModelProperty(value = "Адрес одной строкой (как показывается в списке подсказок)")
    private String value;

    @ApiModelProperty(value = "Данные адреса")
    private AddressDataDto data = new AddressDataDto();

    public AddressDto() {
    }

    public AddressDto(String value, AddressDataDto data) {
        this.value = value;
        this.data = data;
    }
}

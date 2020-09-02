package service.proxy.dadata.model.transport.clean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(
        value = "Данные адреса",
        description = "Сущьность данных адреса из ответа DaData")
@Data
public class AddressCleanDataDto {

    @ApiModelProperty(value = "Индекс")
    private String postal_code;
    @ApiModelProperty(value = "Регион")
    private String region;
    @ApiModelProperty(value = "Город")
    private String city;
    @ApiModelProperty(value = "Населенный пункт")
    private String settlement;
    @ApiModelProperty(value = "Улица")
    private String street;
    @ApiModelProperty(value = "Дом")
    private String house;
}

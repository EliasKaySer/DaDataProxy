package service.proxy.models.transports.dadata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(
        value = "Адрес (Полный)",
        description = "Сущность адреса из ответа DaData")
@Data
public class DaDataSuggestionsDto {
    @ApiModelProperty(value = "Адрес одной строкой (как показывается в списке подсказок)")
    private String value;

    @ApiModelProperty(value = "Адрес одной строкой (полный, с индексом)")
    private String unrestricted_value;

    @ApiModelProperty(value = "Данные адреса")
    private DaDataSuggestionsDataDto data = new DaDataSuggestionsDataDto();
}

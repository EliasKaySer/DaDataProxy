package service.proxy.models.transports;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;
import service.proxy.models.entityes.Address;

@ApiModel(
        value = "Адрес",
        description = "Сущность адреса из ответа DaData")
@Data
public class AddressDto {

    @ApiModelProperty(value = "Адрес одной строкой (как показывается в списке подсказок)")
    private String value;

    @ApiModelProperty(value = "Данные адреса")
    private AddressDataDto data = new AddressDataDto();

    public String setValue() {
        String value = StringUtils.hasText(this.data.getPostal_code()) ? this.data.getPostal_code() : "";
        value += StringUtils.hasText(this.data.getRegion())
                ? (String.format("%s%s", StringUtils.hasText(value) ? ", " : "", this.data.getRegion()))
                : "";
        value += StringUtils.hasText(this.data.getCity())
                ? (String.format("%sг %s", StringUtils.hasText(value) ? ", " : "", this.data.getCity()))
                : "";
        value += StringUtils.hasText(this.data.getSettlement())
                ? (String.format("%s%s", StringUtils.hasText(value) ? ", " : "", this.data.getSettlement()))
                : "";
        value += StringUtils.hasText(this.data.getStreet())
                ? (String.format("%sул %s", StringUtils.hasText(value) ? ", " : "", this.data.getStreet()))
                : "";
        value += StringUtils.hasText(this.data.getHouse())
                ? (String.format("%sд %s", StringUtils.hasText(value) ? ", " : "", this.data.getHouse()))
                : "";
        return value;
    }
}

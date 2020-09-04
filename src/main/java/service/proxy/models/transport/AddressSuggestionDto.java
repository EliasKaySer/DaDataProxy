package service.proxy.models.transport;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.util.StringUtils;
import service.proxy.models.entity.Address;

import java.util.ArrayList;
import java.util.List;

@ApiModel(
        value = "Подсказка",
        description = "Адрес одной строкой")
@Data
public class AddressSuggestionDto {

    private String value = "";

    public AddressSuggestionDto (Address entity) {

        this.value = (StringUtils.hasText(entity.getPostalcode()) ? entity.getPostalcode() : "")
                + (StringUtils.hasText(entity.getRegion()) ? String.format(", %s", entity.getRegion()) : "")
                + (StringUtils.hasText(entity.getCity()) ? String.format(", г %s", entity.getCity()) : "")
                + (StringUtils.hasText(entity.getSettlement()) ? String.format(", %s", entity.getSettlement()) : "")
                + (StringUtils.hasText(entity.getStreet()) ? String.format(", ул %s", entity.getStreet()) : "")
                + (StringUtils.hasText(entity.getHouse()) ? String.format(", д %s", entity.getHouse()) : "");
    }
}

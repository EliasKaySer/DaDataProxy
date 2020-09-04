package service.proxy.models.transport;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;
import service.proxy.models.entity.Address;

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

    public AddressDto(Address entity) {

//        AddressId addressId = entity.getAddressId();
//
//        this.data.setPostal_code(addressId.getPostalcode());
//        this.data.setRegion(addressId.getRegion());
//        this.data.setCity(addressId.getCity());
//        this.data.setSettlement(addressId.getSettlement());
//        this.data.setStreet(addressId.getStreet());
//        this.data.setHouse(addressId.getHouse());

        this.data.setPostal_code(entity.getPostalcode());
        this.data.setRegion(entity.getRegion());
        this.data.setCity(entity.getCity());
        this.data.setSettlement(entity.getSettlement());
        this.data.setStreet(entity.getStreet());
        this.data.setHouse(entity.getHouse());

        this.value = entity.getValue();
//        this.value = (StringUtils.hasText(this.data.getPostal_code()) ? this.data.getPostal_code() : "")
//                + (StringUtils.hasText(this.data.getRegion()) ? String.format(", %s", this.data.getRegion()) : "")
//                + (StringUtils.hasText(this.data.getCity()) ? String.format(", г %s", this.data.getCity()) : "")
//                + (StringUtils.hasText(this.data.getSettlement()) ? String.format(", %s", this.data.getSettlement()) : "")
//                + (StringUtils.hasText(this.data.getStreet()) ? String.format(", ул %s", this.data.getStreet()) : "")
//                + (StringUtils.hasText(this.data.getHouse()) ? String.format(", д %s", this.data.getHouse()) : "");
    }

}

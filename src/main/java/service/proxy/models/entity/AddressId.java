package service.proxy.models.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.StringUtils;
import service.proxy.models.transport.AddressDataDto;
import service.proxy.models.transport.AddressDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ApiModel
@Data
@Embeddable
public class AddressId implements Serializable {
    public AddressId() {

    }

    @ApiModelProperty(value = "Индекс")
    @Column(name = "postalcode", nullable = true)
    private String postalcode;

    @ApiModelProperty(value = "Регион")
    @Column(name = "region", nullable = true)
    private String region;

    @ApiModelProperty(value = "Город")
    @Column(name = "city", nullable = true)
    private String city;

    @ApiModelProperty(value = "Населенный пункт")
    @Column(name = "settlement", nullable = true)
    private String settlement;

    @ApiModelProperty(value = "Улица")
    @Column(name = "street", nullable = true)
    private String street;

    @ApiModelProperty(value = "Дом")
    @Column(name = "house", nullable = true)
    private String house;

    public AddressId(AddressDataDto dto) {
        this.postalcode = StringUtils.hasText(dto.getPostal_code()) ? dto.getPostal_code() : "нет";
        this.region = StringUtils.hasText(dto.getRegion()) ? dto.getRegion() : "нет";
        this.city = StringUtils.hasText(dto.getCity()) ? dto.getCity() : "нет";
        this.settlement = StringUtils.hasText(dto.getSettlement()) ? dto.getSettlement() : "нет";
        this.street = StringUtils.hasText(dto.getStreet()) ? dto.getStreet() : "нет";
        this.house = StringUtils.hasText(dto.getHouse()) ? dto.getHouse() : "нет";
    }
}

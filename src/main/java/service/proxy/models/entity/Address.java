package service.proxy.models.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.StringUtils;
import service.proxy.models.transport.AddressDataDto;
import service.proxy.models.transport.AddressDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ApiModel(
        value = "Адрес",
        description = "Сущность Адрес")
@Data
@Entity
@Table(name = "addresses",
        indexes = {@Index(name = "addresses__postalcode_region_city_settlement_street_house__uindex",
                columnList = "postalcode, region, city, settlement, street, house",
                unique = true),
                @Index(name = "addresses__value__uindex",
                        columnList = "value",
                        unique = true)})
public class Address {
    public Address() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    @ApiModelProperty(value = "Индекс")
    @Column(name = "postalcode")
    private String postalcode;

    @ApiModelProperty(value = "Регион")
    @Column(name = "region")
    private String region;

    @ApiModelProperty(value = "Город")
    @Column(name = "city")
    private String city;

    @ApiModelProperty(value = "Населенный пункт")
    @Column(name = "settlement")
    private String settlement;

    @ApiModelProperty(value = "Улица")
    @Column(name = "street")
    private String street;

    @ApiModelProperty(value = "Дом")
    @Column(name = "house")
    private String house;

    @ApiModelProperty(value = "Адрес одной строкой (как показывается в списке подсказок)")
    @Column(name = "value", unique = true)
    private String value;

    @ApiModelProperty(value = "Связанные ответы")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cross_requests_addresses",
            joinColumns = @JoinColumn(name = "requests_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "addresses_id", referencedColumnName = "id"))
    private Set<Request> requests = new HashSet<>();

    public Address(AddressDto dto) {
        AddressDataDto data = dto.getData();
        this.postalcode = data.getPostal_code();
        this.region = data.getRegion();
        this.city = data.getCity();
        this.settlement = data.getSettlement();
        this.street = data.getStreet();
        this.house = data.getHouse();

        this.value = (StringUtils.hasText(this.postalcode) ? this.postalcode : "")
                + (StringUtils.hasText(this.region) ? String.format(", %s", this.region) : "")
                + (StringUtils.hasText(this.city) ? String.format(", г %s", this.city) : "")
                + (StringUtils.hasText(this.settlement) ? String.format(", %s", this.settlement) : "")
                + (StringUtils.hasText(this.street) ? String.format(", ул %s", this.street) : "")
                + (StringUtils.hasText(this.house) ? String.format(", д %s", this.house) : "");

    }

    public void setData(Address data) {
        this.postalcode = data.getPostalcode();
        this.region = data.getRegion();
        this.city = data.getCity();
        this.settlement = data.getSettlement();
        this.street = data.getStreet();
        this.house = data.getHouse();
    }
}

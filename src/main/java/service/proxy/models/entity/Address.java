package service.proxy.models.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import service.proxy.models.transport.AddressDataDto;
import service.proxy.models.transport.AddressDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ApiModel
@Data
@Entity
//@Table(name = "Addresses")
@Table(name = "Addresses",
        indexes = {@Index(name = "addresses_content_uindex",
                columnList = "postalcode, region, city, settlement, street, house",
                unique = true
        )
        })
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

//    TODO: set this.value is unique
    @ApiModelProperty(value = "Адрес одной строкой (как показывается в списке подсказок)")
    @Column(name = "value", unique = false)
    private String value;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cross_requests_addresses",
            joinColumns = @JoinColumn(name = "requests_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "addresses_id", referencedColumnName = "id"))
    private Set<Request> requests = new HashSet<>();

//    @EmbeddedId
//    private AddressId addressId;

    public Address(AddressDto dto) {

//        this.addressId = new AddressId(dto.getData());

        AddressDataDto data = dto.getData();
        this.postalcode = data.getPostal_code();
        this.region = data.getRegion();
        this.city = data.getCity();
        this.settlement = data.getSettlement();
        this.street = data.getStreet();
        this.house = data.getHouse();

        this.value = dto.getValue();
//        this.value = (StringUtils.hasText(this.postalcode) ? this.postalcode : "")
//                + (StringUtils.hasText(this.region) ? String.format(", %s", this.region) : "")
//                + (StringUtils.hasText(this.city) ? String.format(", г %s", this.city) : "")
//                + (StringUtils.hasText(this.settlement) ? String.format(", %s", this.settlement) : "")
//                + (StringUtils.hasText(this.street) ? String.format(", ул %s", this.street) : "")
//                + (StringUtils.hasText(this.house) ? String.format(", д %s", this.house) : "");
    }
}

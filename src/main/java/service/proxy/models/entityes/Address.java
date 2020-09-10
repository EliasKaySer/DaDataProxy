package service.proxy.models.entityes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApiModel(
        value = "Адрес",
        description = "Сущность Адрес")
@Data
@Entity
@Table(name = "addresses")
//@Table(name = "addresses",
//        indexes = @Index(name = "addresses__postalcode_region_city_settlement_street_house__uindex",
//                columnList = "postalcode, region, city, settlement, street, house",
//                unique = true))
@ToString(exclude = "requests")
public class Address {
    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
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

    @ApiModelProperty(value = "Связанные запросы")
    @ManyToMany(mappedBy = "addresses", fetch = FetchType.LAZY)
    private List<Request> requests = new ArrayList<>();

    public Address() {
    }
}

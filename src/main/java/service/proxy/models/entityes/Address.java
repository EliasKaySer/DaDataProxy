package service.proxy.models.entityes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

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
@ToString(exclude = "requests")
public class Address {
    @Id
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

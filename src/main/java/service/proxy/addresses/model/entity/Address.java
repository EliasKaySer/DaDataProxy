package service.proxy.addresses.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import service.proxy.dadata.model.transport.clean.AddressCleanDTO;
import service.proxy.dadata.model.transport.clean.AddressCleanDataDto;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "Addresses")
public class Address {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;
    @Column(name = "postal_code")
    private String postal_code;
    @Column(name = "region")
    private String region;
    @Column(name = "city")
    private String city;
    @Column(name = "settlement")
    private String settlement;
    @Column(name = "street")
    private String street;
    @Column(name = "house")
    private String house;

    @ManyToOne(optional=false)
    @JoinColumn(name="request_id", referencedColumnName="id")
    private Request request;


    public Address() {
    }

    public Address(AddressCleanDTO dto) {

        AddressCleanDataDto data = dto.getData();

        this.postal_code = data.getPostal_code();
        this.region = data.getRegion();
        this.city = data.getCity();
        this.settlement = data.getSettlement();
        this.street = data.getStreet();
        this.house = data.getHouse();
    }


}

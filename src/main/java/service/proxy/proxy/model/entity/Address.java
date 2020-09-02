package service.proxy.proxy.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

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
    @Column(name = "id", updatable = false, nullable = false)
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
}

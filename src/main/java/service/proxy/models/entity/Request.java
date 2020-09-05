package service.proxy.models.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@ApiModel(
        value = "Запросы",
        description = "Сущность запроса")
@Data
@Entity
@Table(name = "requests",
        indexes = @Index(name = "requests__query__uindex",
                columnList = "query",
                unique = true))
public class Request {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;
    @ApiModelProperty(value = "Текст запроса")
    @Column(name = "query", unique = true, nullable = false)
    private String query;
    @ApiModelProperty(value = "Количество запросов")
    @Column(name = "count", nullable = false)
    private Integer count = 1;
    @ApiModelProperty(value = "Количество ответов")
    @Column(name = "responses", nullable = false)
    private Integer responses = 0;
    @ApiModelProperty(value = "Дата вызова вопроса")
    @Column(name = "date", nullable = false)
    private Long date = new Date().getTime();

    @ApiModelProperty(value = "Связанные адреса")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "cross_requests_addresses",
            joinColumns = @JoinColumn(name = "requests_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "addresses_id", referencedColumnName = "id")
    )
    private Set<Address> addresses = new HashSet<>();

    public Request() {
    }

    public Request(String query) {
        this.query = query;
    }

    public void IncCount() {
        this.count++;
    }

    public void setAddresses(Set<Address> addresses, Set<Address> entityes) {
        boolean addressesIsNull = addresses.size() == 0 || addresses.stream().noneMatch(Objects::nonNull);
        boolean entityesIsNull = entityes.size() == 0 || entityes.stream().noneMatch(Objects::nonNull);
        if (!addressesIsNull && !entityesIsNull) {
            addresses.removeIf(a -> entityes.stream().map(Address::getValue)
                    .collect(Collectors.toSet()).contains(a.getValue()));
            addressesIsNull = addresses.size() == 0 || addresses.stream().noneMatch(Objects::nonNull);
        }
        if (!addressesIsNull) {
            if (this.addresses.size() == 0) {
                this.addresses.addAll(addresses);
            } else {
                for (Address address : addresses) {
                    Address thisAddress = this.addresses.stream()
                            .filter(a -> a.getValue().equals(address.getValue()))
                            .findFirst().orElse(null);
                    if (thisAddress != null) {
                        this.addresses.add(address);
                    } else {
                        thisAddress.setData(address);
                    }
                }
            }
        }
        if (!entityesIsNull) {
            if (this.addresses.size() == 0) {
                this.addresses.addAll(entityes);
            } else {
                for (Address address : entityes) {
                    Address thisAddress = this.addresses.stream()
                            .filter(a -> a.getValue().equals(address.getValue()))
                            .findFirst().orElse(null);
                    if (thisAddress == null) {
                        this.addresses.add(address);
                    }
                }
            }
        }
    }
}

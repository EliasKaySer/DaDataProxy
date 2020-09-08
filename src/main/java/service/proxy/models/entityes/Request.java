package service.proxy.models.entityes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ApiModel(
        value = "Запросы",
        description = "Сущность запроса")
@Data
@Entity
@Table(name = "requests",
        indexes = @Index(name = "requests__query__uindex", columnList = "query", unique = true))
@ToString(exclude = "addresses")
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
    private Integer count = 0;
    @ApiModelProperty(value = "Количество ответов")
    @Column(name = "responses", nullable = false)
    private Integer responses = 0;
    @ApiModelProperty(value = "Дата вызова вопроса")
    @Column(name = "date", nullable = false)
    private Long date = new Date().getTime();

    @ApiModelProperty(value = "Связанные адреса")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "requests_addresses",
            joinColumns = @JoinColumn(name = "request_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id"))
    private List<Address> addresses = new ArrayList<>();

    public Request() {
    }

    public Request(String query) {
        this.query = query;
    }

    public void incremetRequest() {
        this.count++;
        this.date = new Date().getTime();
        this.responses = this.addresses.size();
    }
}

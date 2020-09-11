package service.proxy.models.entityes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApiModel(
        value = "Запросы",
        description = "Сущность запроса")
@Data
@Entity
@Table(name = "requests")
//@Table(name = "requests",
//        indexes = @Index(name = "requests__query__uindex", columnList = "query", unique = true))
@ToString(exclude = "addresses")
public class Request {
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
    @ApiModelProperty(value = "Текст запроса")
//    @Column(name = "query", unique = true, nullable = false)
    @Column(name = "query")
    private String query;
    @ApiModelProperty(value = "Количество запросов")
//    @Column(name = "count", nullable = false)
    @Column(name = "count")
    private Integer count = 0;
    @ApiModelProperty(value = "Количество ответов")
//    @Column(name = "responses", nullable = false)
    @Column(name = "responses")
    private Integer responses = 0;
    @ApiModelProperty(value = "Дата вызова вопроса")
//    @Column(name = "date", nullable = false)
    @Column(name = "date")
    private Instant date = Instant.now();
//    private Long date = new Date().getTime();

    @ApiModelProperty(value = "Связанные адреса")
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "requests_addresses",
            joinColumns = @JoinColumn(name = "request_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id"))
    private List<Address> addresses = new ArrayList<>();

    public Request() {
    }

    public Request(String query) {
        this.query = query;
    }
}

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
@ToString(exclude = "addresses")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @ApiModelProperty(value = "Текст запроса")
    @Column(name = "query")
    private String query;
    @ApiModelProperty(value = "Количество запросов")
    @Column(name = "count")
    private Integer count = 0;
    @ApiModelProperty(value = "Количество ответов")
    @Column(name = "responses")
    private Integer responses = 0;
    @ApiModelProperty(value = "Дата вызова вопроса")
    @Column(name = "date")
    private Instant date = Instant.now();

    @ApiModelProperty(value = "Связанные адреса")
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "requests_addresses",
            joinColumns = @JoinColumn(name = "request_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id"))
    private List<Address> addresses = new ArrayList<>();
}

package service.proxy.models.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ApiModel
@Data
@Entity
@Table(name = "Requests")
public class Request {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;
    @Column(name = "query", unique = true, nullable = false)
    private String query;
    @Column(name = "count", nullable = false)
    private Integer count = 1;
    @Column(name = "responses", nullable = false)
    private Integer responses = 0;
    @Column(name = "date", nullable = false)
    private Long date = new Date().getTime();

//    @OneToMany(mappedBy = "requests", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(name = "cross_requests_addresses",
            joinColumns = @JoinColumn(name = "requests_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "addresses_id", referencedColumnName = "id"))
    private Set<Address> addresses = new HashSet<>();

    public Request() {
    }

    public Request(String query) {
        this.query = query;
    }

    public void IncCount() {
        this.count++;
    }
}

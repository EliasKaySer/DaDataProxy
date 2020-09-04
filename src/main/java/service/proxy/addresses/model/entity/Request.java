package service.proxy.addresses.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

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
    @Column(name = "query", unique=true)
    private String query;
    @Column(name = "count")
    private Integer queryCount = 1;
    @Column(name = "responseCount")
    private Integer responseCount;
    @Column(name = "date")
    private Date date = new Date();

    @OneToMany(mappedBy="request", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Address> addresses;

    public Request() {
    }

    public Request(String query){
        this.query = query;
    }

    public Request(String query, List<Address> addresses){
        this.query = query;
        this.responseCount = addresses.size();
        this.addresses = (Set<Address>) addresses;
    }

    public void IncCount(){
        this.queryCount++;
    }
}

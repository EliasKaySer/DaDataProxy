package service.proxy.addresses.repository;

import org.springframework.data.repository.CrudRepository;
import service.proxy.addresses.model.entity.Request;

import java.util.UUID;

public interface RequestRepository extends CrudRepository<Request, UUID> {


    Request findByQuery(String query);
}

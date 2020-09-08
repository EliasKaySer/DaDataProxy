package service.proxy.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import service.proxy.models.entityes.Request;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RequestRepository extends CrudRepository<Request, UUID> {

    Optional<Request> findByQuery(String query);

    Set<Request> findByDateLessThanEqualAndResponsesLessThan(long previous, int count);
}

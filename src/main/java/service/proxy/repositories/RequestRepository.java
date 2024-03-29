package service.proxy.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import service.proxy.models.entityes.Request;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestRepository extends CrudRepository<Request, UUID> {

    Optional<Request> findByQuery(String query);

    List<Request> findByDateBeforeAndResponsesLessThan(Instant instant, int responses);

    List<Request> findByDateBeforeAndResponsesGreaterThanEqual(Instant instant, int responses);
}

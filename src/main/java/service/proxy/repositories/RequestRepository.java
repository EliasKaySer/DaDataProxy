package service.proxy.repositories;

import org.springframework.data.repository.CrudRepository;
import service.proxy.models.entity.Request;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public interface RequestRepository extends CrudRepository<Request, UUID> {

    Request findByQuery(String query);

    Iterable<Request> findByDateLessThanEqualAndResponsesLessThan(Long previous, int count);
}

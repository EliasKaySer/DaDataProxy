package service.proxy.repositories;

import org.springframework.data.repository.CrudRepository;
import service.proxy.models.entity.Address;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AddressRepository extends CrudRepository<Address, UUID> {

    Set<Address> findAllByValue(String value);

    Address findByValue(String value);
}

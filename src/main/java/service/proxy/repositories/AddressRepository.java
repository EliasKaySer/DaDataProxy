package service.proxy.repositories;

import org.springframework.data.repository.CrudRepository;
import service.proxy.models.entity.Address;

import java.util.UUID;

public interface AddressRepository extends CrudRepository<Address, UUID> {
    Address findByValue(String value);
}

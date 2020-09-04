package service.proxy.addresses.repository;

import org.springframework.data.repository.CrudRepository;
import service.proxy.addresses.model.entity.Address;

import java.util.UUID;

public interface AddressRepository extends CrudRepository<Address, UUID> {
}

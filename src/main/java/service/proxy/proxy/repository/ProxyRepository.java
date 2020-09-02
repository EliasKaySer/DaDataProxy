package service.proxy.proxy.repository;

import org.springframework.data.repository.CrudRepository;
import service.proxy.proxy.model.entity.Address;

import java.util.UUID;

public interface ProxyRepository extends CrudRepository<Address, UUID> {
}

package service.proxy.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import service.proxy.models.entityes.Address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends CrudRepository<Address, UUID> {

    List<Address> findAll();

    List<Address> findAllByRegionLikeIgnoreCase(String region);

    List<Address> findAllByCityLikeIgnoreCase(String city);

    List<Address> findAllBySettlementLikeIgnoreCase(String settlement);

    List<Address> findAllByStreetLikeIgnoreCase(String street);

    Optional<Address> findByPostalcodeAndRegionAndCityAndSettlementAndStreetAndHouse(String postalcode, String region, String city, String settlement, String street, String house);

}


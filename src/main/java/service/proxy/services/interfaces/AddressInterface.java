package service.proxy.services.interfaces;

import service.proxy.models.entityes.Address;
import service.proxy.models.transports.AddressDto;

import java.util.List;

public interface AddressInterface {

    List<Address> getAllAddresses();

    List<Address> getAddresses(String region, String city, String settlement, String street);

    List<Address> getSuggestions(String query);

    List<Address> getSuggestions(String query, Integer count, String language);

    void clean();
}

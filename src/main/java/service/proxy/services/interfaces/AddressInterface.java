package service.proxy.services.interfaces;

import service.proxy.models.transports.AddressDto;

import java.util.List;

public interface AddressInterface {

    List<AddressDto> getAllAddresses();

    List<AddressDto> getAddresses(String region, String city, String settlement, String street);

    List<String> getSuggestions(String query);

    List<String> getSuggestions(String query, Integer count, String language);
}

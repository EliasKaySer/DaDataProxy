package service.proxy.services.interfaces;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import service.proxy.models.transports.AddressDto;

import java.util.List;

public interface AddressInterface {

    List<AddressDto> getAllAddresses();

    List<AddressDto> getAddresses(String region, String city, String settlement, String street);

    List<String> getSuggestions(@NonNull String query);

    List<String> getSuggestions(@NonNull String query, @Nullable Integer count, @Nullable String language);
}

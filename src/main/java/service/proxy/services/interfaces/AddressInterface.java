package service.proxy.services.interfaces;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import service.proxy.models.entity.Address;
import service.proxy.models.entity.Request;
import service.proxy.models.transport.AddressDto;
import service.proxy.models.transport.AddressListDto;
import service.proxy.models.transport.AddressSuggestionDto;

import java.util.List;

public interface AddressInterface {

    Iterable<Address> getAllAddress();

    List<String> getAddresses(@NonNull String query);

    List<String> getAddresses(@NonNull String query, @Nullable Integer count, @Nullable String language);

    AddressListDto doRequest(@NonNull String query, @Nullable Integer count, @Nullable String language);

    Iterable<Request> getAllRequest();
}
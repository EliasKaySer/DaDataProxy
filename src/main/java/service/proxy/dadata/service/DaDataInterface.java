package service.proxy.dadata.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import service.proxy.addresses.model.entity.Address;
import service.proxy.dadata.model.transport.DaDataResponseDTO;
import service.proxy.dadata.model.transport.clean.AddressCleanDTO;

import java.util.List;
import java.util.Map;

public interface DaDataInterface {

    @NonNull
    List<AddressCleanDTO> getAddresses(@NonNull String query, @Nullable Integer count, @Nullable String language);

}

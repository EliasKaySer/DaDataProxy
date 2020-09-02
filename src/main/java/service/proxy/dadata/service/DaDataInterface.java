package service.proxy.dadata.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import service.proxy.dadata.model.transport.DaDataResponseDTO;

import java.util.Map;

public interface DaDataInterface {

    @NonNull
    DaDataResponseDTO getAddresses(@NonNull String query, @Nullable Integer count, @Nullable String language);

}

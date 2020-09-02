package service.proxy.dadata.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;

public interface DaDataInterface {

    @NonNull
    Map<String, String> Addresses(@NonNull String query, @Nullable Integer count, @Nullable String language);

}

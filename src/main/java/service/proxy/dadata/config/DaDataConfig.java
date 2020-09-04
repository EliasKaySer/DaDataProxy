package service.proxy.dadata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.proxy.dadata.service.DaDataService;

@Configuration
public class DaDataConfig {
    static String API_KEY;
    static String SECRET_KEY;
    static String BASE_URI;
    
    DaDataConfig(@Value("${dadata.api-key}") final String apiKey,
                 @Value("${dadata.secret-key}") final String secretKey,
                 @Value("${dadata.base-uri}") final String baseUri) {
        API_KEY = apiKey;
        SECRET_KEY = secretKey;
        BASE_URI = baseUri;
    }

    @Bean
    DaDataService dadataBean() {
        return new DaDataService(API_KEY, SECRET_KEY, BASE_URI);
    }
}

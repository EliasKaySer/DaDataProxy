package service.proxy.dadata.service;

import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import service.proxy.dadata.model.transport.DaDataResponseDTO;
import springfox.documentation.spring.web.json.Json;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DaDataService implements DaDataInterface {

    private static final String DADATA_API_BASE_URI = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest";
    private static final String DADADA_API_ADDRESS = "/address";

    @NonNull
    private final String apiKey;
    @NonNull
    private final String secretKey;
    @NonNull
    private final String baseUri;

    public DaDataService(String apiKey, String secretKey, String baseUri) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.baseUri = StringUtils.hasText(baseUri) ? baseUri : DADATA_API_BASE_URI;
    }

    @Override
    public final DaDataResponseDTO getAddresses(String query, Integer count, String language) {
// request url
        String url = this.baseUri + DADADA_API_ADDRESS;

// create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

// create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.AUTHORIZATION, "Token " + this.apiKey);
//        headers.set("X-Secret", this.secretKey);

// request body parameters
        Map<String, Object> map = new HashMap<>();
        map.put("query", query);
        map.put("count", count);
        map.put("language", language);

// build the request
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);

// send POST request
        ResponseEntity<DaDataResponseDTO> response = restTemplate.postForEntity(url, request, DaDataResponseDTO.class);

//        Map<String, Object> results = new HashMap<>();
//
//        results.put("StatusCode", response.getStatusCode().toString());
//
//// check response
//        if (response.getStatusCode() == HttpStatus.OK) {
//            results.put("Request", "Successful");
//        } else {
//            results.put("Request", "Failed");
//        }
//
//        results.put("Body", response.getBody());ap<String, Object> results = new HashMap<>();



// check response
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return new DaDataResponseDTO();
        }
    }
}

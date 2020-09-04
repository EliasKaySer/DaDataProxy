package service.proxy.dadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import service.proxy.addresses.model.converter.AddressMaper;
import service.proxy.addresses.model.entity.Address;
import service.proxy.dadata.model.transport.DaDataResponseDTO;
import service.proxy.addresses.model.entity.Request;
import service.proxy.addresses.repository.RequestRepository;
import service.proxy.dadata.model.transport.clean.AddressCleanDTO;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    @Autowired
    private RequestRepository requestRepository;

//    @Autowired
//    private AddressMaper addressMaper;


    @Override
    public final List<AddressCleanDTO> getAddresses(String query, Integer count, String language) {

// request body parameters
        Map<String, Object> map = new HashMap<>();
        map.put("query", query);
        map.put("count", count);
        map.put("language", language);

        Request request = requestRepository.findByQuery(query);
        if (request == null){
            request = new Request(query);
        } else {
            request.IncCount();
        }
        request = requestRepository.save(request);

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

// build the request
        HttpEntity<Map<String, Object>> requestDaData = new HttpEntity<>(map, headers);

// send POST request
        ResponseEntity<DaDataResponseDTO> responseDaData = restTemplate.postForEntity(url, requestDaData, DaDataResponseDTO.class);

        List<AddressCleanDTO> suggestions = new ArrayList<>();
// check response
        if (responseDaData.getStatusCode() == HttpStatus.OK) {
            suggestions = responseDaData.getBody().getSuggestions();
            Set<Address> collect = suggestions
                    .stream()
                    .map(a -> new Address(a))
                    .collect(Collectors.toSet())
                    ;
            request.setResponseCount(collect.size());
            request.setAddresses(collect);
            request = requestRepository.save(request);
        }
        return suggestions;
    }
}

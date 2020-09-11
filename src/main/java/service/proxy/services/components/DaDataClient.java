package service.proxy.services.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import service.proxy.models.transports.AddressDataDto;
import service.proxy.models.transports.AddressListDto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DaDataClient {
    @Value("${dadata.apiKey}")
    private String apiKey;
    @Value("${dadata.secretKey}")
    private String secretKey;
    @Value("${dadata.baseUri}")
    private String baseUri;
    private static final String DADATA_SUGGEST_BASE_URI = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest";
    private static final String DADATA_ADDRESS_URI = "/address";
    private static final String SUGGEST_LANGUAGE_DEFAULT = "ru";
    private static final Integer SUGGEST_COUNT_MIN = 1;
    private static final Integer SUGGEST_COUNT_DEFAULT = 10;
    private static final Integer SUGGEST_COUNT_MAX = 20;

    public List<AddressDataDto> getAddresses(String query, Integer count, String language) {
        return doRequest(query, count, language)
                .getSuggestions()
                .stream()
                .map(a -> a.getData())
                .collect(Collectors.toList());
    }

    public AddressListDto doRequest(String query, Integer count, String language) {
        // request url
        String url = (StringUtils.hasText(this.baseUri) ? this.baseUri : DADATA_SUGGEST_BASE_URI) + DADATA_ADDRESS_URI;
        // request body parameters
        Map<String, Object> map = new HashMap<>();
        map.put("query", query);
        map.put("count", (count == null || count < SUGGEST_COUNT_MIN)
                ? SUGGEST_COUNT_DEFAULT : (count > SUGGEST_COUNT_MAX) ? SUGGEST_COUNT_MAX : count);
        map.put("language", StringUtils.hasText(language) ? language : SUGGEST_LANGUAGE_DEFAULT);
        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.AUTHORIZATION, String.format("Token %s", this.apiKey));
//        TODO: Api standart
//        headers.set("X-Secret", this.secretKey);
        // build the request
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
        // send POST request
        ResponseEntity<AddressListDto> responseDaData = restTemplate
                .postForEntity(url, request, AddressListDto.class);
        // check response
        if (responseDaData.getStatusCode() == HttpStatus.OK) {
            return responseDaData.getBody();
        }
        return new AddressListDto();
    }
}

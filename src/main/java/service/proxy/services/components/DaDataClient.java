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
    @Value("${dadata.api-key}")
    private String apiKey;
    @Value("${dadata.secret-key}")
    private String secretKey;
    @Value("${dadata.base-uri}")
    private String baseUri;
    @Value("${dadata.uri-address}")
    private String uriAddress;
    @Value("${dadata.language-default}")
    private String languageDefault;
    @Value("${dadata.suggest-count-min}")
    private Integer suggestCountMin;
    @Value("${dadata.suggest-count-default}")
    private Integer suggestCountDefault;
    @Value("${dadata.suggest-count-max}")
    private Integer suggestCountMax;

    public List<AddressDataDto> getAddresses(String query, Integer count, String language) {
        return doRequest(query, count, language)
                .getSuggestions()
                .stream()
                .map(a -> a.getData())
                .collect(Collectors.toList());
    }

    public AddressListDto doRequest(String query, Integer count, String language) {
        // request url
        String url = this.baseUri + uriAddress;
        // request body parameters
        Map<String, Object> map = new HashMap<>();
        map.put("query", query);
        map.put("count", (count == null || count < suggestCountMin)
                ? suggestCountDefault : (count > suggestCountMax) ? suggestCountMax : count);
        map.put("language", StringUtils.hasText(language) ? language : languageDefault);
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
        HttpEntity<Map<String, Object>> requestDaData = new HttpEntity<>(map, headers);
        // send POST request
        ResponseEntity<AddressListDto> responseDaData = restTemplate
                .postForEntity(url, requestDaData, AddressListDto.class);
        // check response
        if (responseDaData.getStatusCode() == HttpStatus.OK) {
            return responseDaData.getBody();
        }
        return new AddressListDto();
    }
}

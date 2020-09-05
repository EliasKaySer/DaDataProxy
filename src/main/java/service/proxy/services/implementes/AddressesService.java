package service.proxy.services.implementes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import service.proxy.models.entity.Address;
import service.proxy.models.entity.Request;
import service.proxy.models.transport.AddressListDto;
import service.proxy.repositories.AddressRepository;
import service.proxy.repositories.RequestRepository;
import service.proxy.services.interfaces.AddressInterface;

import java.util.*;
import java.util.stream.Collectors;

public class AddressesService implements AddressInterface {

    private static final String DADATA_API_BASE_URI = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest";
    private static final String DADADA_API_ADDRESS = "/address";

    private static final Integer DADADA_API_ADDRESS_COUNT = 10;
    private static final String DADADA_API_ADDRESS_LANGUAGE = "ru";

    @NonNull
    private final String baseUri;
    @NonNull
    private final String apiKey;
    @NonNull
    private final String secretKey;

    public AddressesService(String apiKey, String secretKey, String baseUri) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.baseUri = StringUtils.hasText(baseUri) ? baseUri : DADATA_API_BASE_URI;
    }

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Iterable<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    @Override
    public List<String> getAddresses(String query) {
        return getAddresses(query, null, null);
    }

    @Override
    public final List<String> getAddresses(String query, Integer count, String language) {
        if (StringUtils.isEmpty(query)) {
            return new ArrayList<>();
        }

        Request request = requestRepository.findByQuery(query);
        if (request == null) {
            request = new Request(query);
            Set<Address> addresses = doRequest(query, count, language).getSuggestions()
                    .stream().map(Address::new).collect(Collectors.toSet());
            Set<Address> entityes = addresses.stream().map(a -> addressRepository.findByValue(a.getValue()))
                    .collect(Collectors.toSet());
            request.setAddresses(addresses, entityes);

        } else {
            request.setDate(new Date().getTime());
            request.IncCount();
//            TODO: test
//        if (((new Date()).getTime() - request.getDate()) / (60 * 1000) % 60 > 1) {
            if (((new Date()).getTime() - request.getDate()) / (60 * 60 * 1000) % 24 > 3) {
                Set<Address> addresses = doRequest(query, count, language).getSuggestions()
                        .stream().map(Address::new).collect(Collectors.toSet());
                Set<Address> entityes = addresses.stream().map(a -> addressRepository.findByValue(a.getValue()))
                        .collect(Collectors.toSet());
                request.setAddresses(addresses, entityes);
            }
        }
        request.setResponses(request.getAddresses().size());
        request = requestRepository.save(request);
        return request.getAddresses().stream()
                .map(Address::getValue).collect(Collectors.toList());
    }

    @Override
    public AddressListDto doRequest(String query, Integer count, String language) {
        // request url
        String url = this.baseUri + DADADA_API_ADDRESS;

        // request body parameters
        Map<String, Object> map = new HashMap<>();
        map.put("query", query);
        map.put("count", (count == null || count < 1) ? DADADA_API_ADDRESS_COUNT : (count > 20) ? 20 : count);
        map.put("language", StringUtils.hasText(language) ? language : DADADA_API_ADDRESS_LANGUAGE);

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(HttpHeaders.AUTHORIZATION, "Token " + this.apiKey);
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

    @Autowired
    private RequestRepository requestRepository;

    public Iterable<Request> getAllRequest() {
        return requestRepository.findAll();
    }

    public Iterable<Request> getCleanedRequests() {
        //calculate date
        Calendar cal = Calendar.getInstance();
//        TODO: test
//        cal.add(Calendar.MINUTE, -1);
        cal.add(Calendar.MONTH, -1);

        Iterable<Request> requests = requestRepository
                .findByDateLessThanEqualAndResponsesLessThan(cal.getTime().getTime(), 3);
        requestRepository.deleteAll(requests);
        return requests;
    }
}

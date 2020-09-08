package service.proxy.services.implementes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import service.proxy.models.converters.AddressMaper;
import service.proxy.models.entityes.Address;
import service.proxy.models.entityes.Request;
import service.proxy.models.transports.AddressDto;
import service.proxy.repositories.AddressRepository;
import service.proxy.repositories.RequestRepository;
import service.proxy.services.components.AddressesUtils;
import service.proxy.services.components.DaDataClient;
import service.proxy.services.interfaces.AddressInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressesService implements AddressInterface {
    private final AddressRepository addressRepository;
    private final RequestRepository requestRepository;
    private final AddressMaper addressMaper;
    private final AddressesUtils addressesUtils;
    private final DaDataClient daDataClient;

    @Override
    public List<AddressDto> getAllAddresses() {
        List<AddressDto> addresses = new ArrayList<>();
        addressRepository.findAll()
                .forEach(a -> addresses.add(new AddressDto(addressesUtils.getValueByContent(a), addressMaper.toDto(a))));
        return addresses;
    }

    @Override
    public List<AddressDto> getAddresses(String region, String city, String settlement, String street) {
        return addressesUtils.getAddresses(region, city, settlement, street).stream()
                .map(a -> new AddressDto(addressesUtils.getValueByContent(a), addressMaper.toDto(a)))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSuggestions(String query) {
        return getSuggestions(query, null, null);
    }

    @Override
    @Transactional
    public List<String> getSuggestions(String query, Integer count, String language) {
        if (StringUtils.isEmpty(query)) {
            return new ArrayList<>();
        }
        Request request = requestRepository.findByQuery(query).orElse(new Request(query));
        if (request.getId() == null || ((new Date()).getTime() - request.getDate()) / (60 * 60 * 1000) % 24 > 3) {
            List<Address> addresses = daDataClient.getAddresses(query, count, language)
                    .stream()
                    .map(addressMaper::toEntity)
                    .collect(Collectors.toList());
            request.setAddresses(addressesUtils.getUpdatedRelatedAddressesList(request, addresses));
        }
        request.incremetRequest();
        request = requestRepository.save(request);
        return request.getAddresses().stream()
                .map(a -> addressesUtils.getValueByContent(a)).collect(Collectors.toList());
    }
}

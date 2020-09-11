package service.proxy.services.implementes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import service.proxy.models.converters.AddressMaper;
import service.proxy.models.entityes.Address;
import service.proxy.models.entityes.Request;
import service.proxy.models.transports.AddressDto;
import service.proxy.repositories.AddressRepository;
import service.proxy.services.components.AddressesUtils;
import service.proxy.services.components.DaDataClient;
import service.proxy.services.components.RequestsUtils;
import service.proxy.services.interfaces.AddressInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressesService implements AddressInterface {
    @Value("${requests.obsolete.hours}")
    private int hours;
    @Value("${requests.obsolete.minutes}")
    private int minutes;
    @Value("${requests.obsolete.seconds}")
    private int seconds;

    private final AddressRepository addressRepository;
    private final AddressMaper addressMaper;
    private final AddressesUtils addressesUtils;
    private final RequestsUtils requestsUtils;
    private final DaDataClient daDataClient;

    @Override
    public List<AddressDto> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(a -> new AddressDto(addressesUtils.getAddressValue(a), addressMaper.toDto(a)))
                .collect(Collectors.toList());
    }

    @Override
    public List<AddressDto> getAddresses(String region, String city, String settlement, String street) {
        return addressesUtils.getAddresses(region, city, settlement, street).stream()
                .map(a -> new AddressDto(addressesUtils.getAddressValue(a), addressMaper.toDto(a)))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSuggestions(String query) {
        return getSuggestions(query, null, null);
    }

    @Override
    public List<String> getSuggestions(String query, Integer count, String language) {
        if (StringUtils.isEmpty(query)) {
            return new ArrayList<>();
        }
        Request request = requestsUtils.getRquestByQuery(query);
        if (request.getId() == null
                || requestsUtils.IsObsoleteByTime(request, hours, minutes, seconds)) {
            List<Address> addresses = daDataClient.getAddresses(query, count, language)
                    .stream()
                    .map(addressMaper::toEntity)
                    .collect(Collectors.toList());
            request = requestsUtils.updateRquest(request, addresses);
        } else {
            request = requestsUtils.updateRquest(request);
        }
        return addressesUtils.getAddressesAsStingList(request.getAddresses());
    }
}

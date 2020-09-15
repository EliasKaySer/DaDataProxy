package service.proxy.services.implementes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import service.proxy.models.entityes.Address;
import service.proxy.models.entityes.Request;
import service.proxy.services.components.AddressesUtils;
import service.proxy.services.components.DaDataClient;
import service.proxy.services.components.RequestsUtils;
import service.proxy.services.interfaces.AddressInterface;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressesService implements AddressInterface {
    @Value("${requests.obsolete.hours}")
    private int obsoleteHours;
    @Value("${requests.obsolete.minutes}")
    private int obsoleteMinutes;
    @Value("${requests.obsolete.seconds}")
    private int obsoleteSeconds;

    @Value("${requests.removal.responses}")
    private int removalResponses;
    @Value("${requests.removal.months}")
    private int removalMonths;
    @Value("${requests.removal.hours}")
    private int removalHours;
    @Value("${requests.removal.minutes}")
    private int removalMinutes;
    @Value("${requests.removal.seconds}")
    private int removalSeconds;

    private final AddressesUtils addressesUtils;
    private final RequestsUtils requestsUtils;
    private final DaDataClient daDataClient;

    @Override
    public List<Address> getAllAddresses() {
        return addressesUtils.getAllAddresses();
    }

    @Override
    public List<Address> getAddresses(String region, String city, String settlement, String street) {
        return addressesUtils.getAddresses(region, city, settlement, street);
    }

    @Override
    public List<Address> getSuggestions(String query) {
        return getSuggestions(query, null, null);
    }

    @Override
    public List<Address> getSuggestions(String query, Integer count, String language) {
        if (StringUtils.isEmpty(query)) {
            return new ArrayList<>();
        }
        Request request = requestsUtils.getRquestByQuery(query);
        if (request.getId() == null
                || requestsUtils.IsObsoleteByTime(request, obsoleteHours, obsoleteMinutes, obsoleteSeconds)) {
            List<Address> addresses = daDataClient.getAddresses(query, count, language);
            request = requestsUtils.updateRquest(request, addresses);
        } else {
            request = requestsUtils.updateRquest(request);
        }
        return request.getAddresses();
    }

    @Override
    public void clean() {
        requestsUtils.RemoveObsoleteRequests(
                removalResponses, removalMonths, removalHours, removalMinutes, removalSeconds);
    }
}

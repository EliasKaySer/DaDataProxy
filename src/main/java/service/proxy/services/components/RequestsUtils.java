package service.proxy.services.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import service.proxy.models.entityes.Address;
import service.proxy.models.entityes.Request;
import service.proxy.repositories.AddressRepository;
import service.proxy.repositories.RequestRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestsUtils {

    private final RequestRepository requestRepository;

    private final AddressRepository addressRepository;
    private final AddressesUtils addressesUtils;

    @Transactional
    public Request getRquestByQuery(String query) {
        return requestRepository.findByQuery(query).orElse(new Request(query));
    }

    @Transactional
    public Request updateRquest(Request request, List<Address> addresses) {
        request.setCount(request.getCount() + 1);
        request.setDate(Instant.now());
        request.setAddresses(addressesUtils.getUpdatedRelatedAddressesList(request, addresses));
        request.setResponses(request.getAddresses().size());
        return requestRepository.save(request);
    }

    public Request updateRquest(Request request) {
        request.setCount(request.getCount() + 1);
        request.setDate(Instant.now());
        return requestRepository.save(request);
    }

    public boolean IsObsoleteByTime(Request request, int hours, int minutes, int second) {
        return Duration.between(request.getDate(), Instant.now())
                .minusHours(hours).minusMinutes(minutes).minusSeconds(second)
                .getSeconds() >= 0;
    }

    public void RemoveObsoleteRequests(int responses, int months, int hours, int minutes, int seconds) {
        Instant instant = Instant.now().atZone(ZoneOffset.UTC)
                .minusMonths(months).minusHours(hours).minusMinutes(minutes).minusMinutes(seconds)
                .toInstant();
        List<Request> requests = responses > 0
                ? requestRepository.findByDateBeforeAndResponsesLessThan(instant, responses)
                : requestRepository.findByDateBeforeAndResponsesGreaterThanEqual(instant, responses);
        if (requests.size() > 0) {
            List<UUID> requestsIds = requests.stream().map(Request::getId)
                    .collect(Collectors.toList());
            List<Address> addresses = requests.stream().map(Request::getAddresses)
                    .flatMap(Collection::stream).collect(Collectors.toList());
            requestRepository.deleteAll(requests);
            addressesUtils.removeObsoleteAddresses(requestsIds, addresses);
        }
    }
}
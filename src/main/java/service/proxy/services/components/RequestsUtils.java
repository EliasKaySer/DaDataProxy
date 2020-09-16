package service.proxy.services.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import service.proxy.models.entityes.Address;
import service.proxy.models.entityes.Request;
import service.proxy.repositories.RequestRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestsUtils {
    private final RequestRepository requestRepository;
    private final AddressesUtils addressesUtils;

    @Transactional
    public Request getRquestByQuery(String query) {
        return requestRepository.findByQuery(query).orElse(new Request(query));
    }

    @Transactional
    public Request updateRquest(Request request, List<Address> addresses) {
        request.setAddresses(addressesUtils.getUpdatedRelatedAddressesList(request, addresses));
        request.setResponses(request.getAddresses().size());
        return updateRquest(request);
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

    @Transactional
    public void RemoveObsoleteRequests(int responses, int months, int hours, int minutes, int seconds) {
        Instant instant = Instant.now().atZone(ZoneOffset.UTC)
                .minusMonths(months).minusHours(hours).minusMinutes(minutes).minusMinutes(seconds)
                .toInstant();
        List<Request> requests = responses > 0
                ? requestRepository.findByDateBeforeAndResponsesLessThan(instant, responses)
                : requestRepository.findByDateBeforeAndResponsesGreaterThanEqual(instant, responses);
        List<UUID> requestsIds = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();
        for (Request request : requests) {
            requestsIds.add(request.getId());
            addresses.addAll(request.getAddresses());
            requestRepository.delete(request);
        }
        addressesUtils.removeObsoleteAddresses(
                requestsIds, addresses.stream().distinct().collect(Collectors.toList()));
    }
}

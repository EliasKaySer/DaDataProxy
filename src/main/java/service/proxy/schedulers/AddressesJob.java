package service.proxy.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import service.proxy.models.entityes.Address;
import service.proxy.models.entityes.Request;
import service.proxy.repositories.AddressRepository;
import service.proxy.repositories.RequestRepository;

import java.util.Calendar;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AddressesJob {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Scheduled(cron = "0 0 22 * * ?")
    @Transactional
    public void cleaner() {
        //calculate date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);

        Set<Request> requests = requestRepository
                .findByDateLessThanEqualAndResponsesLessThan(cal.getTime().getTime(), 3);
        if (requests.size() > 0) {
            Set<Address> addresses = requests.stream().map(Request::getAddresses)
                    .flatMap(Collection::stream).collect(Collectors.toSet());
            requestRepository.deleteAll(requests);
            addressRepository.deleteAll(addresses.stream()
                    .filter(a -> a.getRequests().size() == 0).collect(Collectors.toList()));
        }
        requestRepository.deleteAll(requests);
    }
}

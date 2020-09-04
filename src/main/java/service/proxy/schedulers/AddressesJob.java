package service.proxy.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import service.proxy.models.entity.Request;
import service.proxy.repositories.RequestRepository;

import java.util.Calendar;
import java.util.Date;

@Component
public class AddressesJob {

    @Autowired
    private RequestRepository requestRepository;

    @Scheduled(cron = "0 0 22 * * ?")
    public void cleaner() {
        //calculate date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Long previous = cal.getTime().getTime();

        Iterable<Request> requests = requestRepository.findByDateLessThanEqualAndResponsesLessThan(previous, 3);
        requestRepository.deleteAll(requests);
    }
}

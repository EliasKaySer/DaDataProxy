package service.proxy.schedulers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import service.proxy.services.components.RequestsUtils;

@Component
@RequiredArgsConstructor
public class AddressesJob {
    @Value("${requests.removal.responses}")
    private int responses;
    @Value("${requests.removal.month}")
    private int month;
    @Value("${requests.removal.hours}")
    private int hours;
    @Value("${requests.removal.minutes}")
    private int minutes;
    @Value("${requests.removal.seconds}")
    private int seconds;

    private final RequestsUtils requestsUtils;

    @Scheduled(cron = "${requests.removal.cron}")
    @Transactional
    public void cleaner() {
        requestsUtils.RemoveObsoleteRequests(responses, month, hours, minutes, seconds);
    }
}

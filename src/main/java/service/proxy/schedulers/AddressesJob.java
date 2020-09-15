package service.proxy.schedulers;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import service.proxy.services.implementes.AddressesService;

@Component
@RequiredArgsConstructor
public class AddressesJob {

    private final AddressesService addressesService;

    @Scheduled(cron = "${requests.removal.cron}")
    @Transactional
    public void cleaner() {
        addressesService.clean();
    }
}

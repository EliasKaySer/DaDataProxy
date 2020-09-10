package service.proxy.IntegrationTests;

import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import service.proxy.controllers.AddressesController;
import service.proxy.models.transports.AddressListDto;
import service.proxy.services.components.DaDataClient;
import service.proxy.services.implementes.AddressesService;

import static org.assertj.core.api.Assertions.assertThat;
import static service.proxy.IntegrationTests.TestCasesAddresses.SampleAddresses;
import static service.proxy.IntegrationTests.TestCasesAddresses.SuccessTest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CommonTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AddressesController addressesController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testNotNullController() throws Exception {
        assertThat(addressesController).isNotNull();
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {

        String uri = String.format("http://localhost:%s/api/v1/address/630058 Новосибирск Вахтангова 5А", port);
        assertThat(uri).isEqualTo("http://localhost:" + port + "/api/v1/address/630058 Новосибирск Вахтангова 5А");
        String forObject = this.restTemplate.getForObject(uri, String.class);
        assertThat(forObject);

        testAddress(SampleAddresses.NSK_VAHTANGOVA_5A);


    }

    private void testAddress(final SampleAddresses sample){
        SuccessTest(this.restTemplate,
                String.format("http://localhost:%s/api/v1/address/", port),
                sample.getRequest(),
                sample.getResponse()
        );
    }
}

package service.proxy.IntegrationTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;
import service.proxy.controllers.AddressesController;
import service.proxy.services.components.AddressesUtils;
import service.proxy.services.components.DaDataClient;
import service.proxy.services.components.RequestsUtils;
import service.proxy.services.implementes.AddressesService;

import static org.assertj.core.api.Assertions.assertThat;
import static service.proxy.IntegrationTests.TestCasesAddresses.SampleAddresses;
import static service.proxy.IntegrationTests.TestCasesAddresses.addressesTest;
import static service.proxy.IntegrationTests.TestCasesSuggestions.SampleSuggestions;
import static service.proxy.IntegrationTests.TestCasesSuggestions.suggestionsTest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CommonTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AddressesController addressesController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RequestsUtils requestsUtils;

    @Autowired
    private AddressesUtils addressesUtils;

    @Autowired
    private DaDataClient daDataClient;

    @Autowired
    private AddressesService addressesService;

    @Test
    public void NotNullServiceAndComponents() throws Exception {
        assertThat(addressesService).isNotNull();
        assertThat(requestsUtils).isNotNull();
        assertThat(addressesUtils).isNotNull();
        assertThat(daDataClient).isNotNull();
    }

    @Test
    public void NotNullController() throws Exception {
        assertThat(addressesController).isNotNull();
    }

    @Test
    public void suggestVahtangova5A() throws Exception {
        testSuggestions(SampleSuggestions.NSK_VAHTANGOVA_5A);
    }

    @Test
    public void suggestShluzovaia19() throws Exception {
        testSuggestions(SampleSuggestions.NSK_SHLUZOVAIA_19);
    }

    @Test
    public void addressVahtangova5A() throws Exception {
        testAddresses(SampleAddresses.NSK_VAHTANGOVA_5A);
    }

    @Test
    public void addressShluzovaia19() throws Exception {
        testAddresses(SampleAddresses.NSK_SHLUZOVAIA_19);
    }

    @Test
    @Transactional
    public void clean() throws Exception {
        requestsUtils.RemoveObsoleteRequests(0, 0, 0, 0, 0);
    }

    private void testSuggestions(final SampleSuggestions sample) {
        suggestionsTest(this.restTemplate, port,
                sample.getRequest(), sample.getResponse());
    }

    private void testAddresses(final SampleAddresses sample) {
        addressesTest(this.restTemplate, port,
                sample.getRequest(), sample.getResponse());
    }
}

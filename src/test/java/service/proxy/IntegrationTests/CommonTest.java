package service.proxy.IntegrationTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import service.proxy.controllers.AddressesController;
import service.proxy.repositories.RequestRepository;
import service.proxy.services.components.AddressesUtils;
import service.proxy.services.components.DaDataClient;
import service.proxy.services.components.RequestsUtils;
import service.proxy.services.implementes.AddressesService;

import static org.assertj.core.api.Assertions.assertThat;
import static service.proxy.IntegrationTests.TestCaseFillDatabase.SampleData;
import static service.proxy.IntegrationTests.TestCaseFillDatabase.fillData;
import static service.proxy.IntegrationTests.TestCasesAddresses.SampleAddresses;
import static service.proxy.IntegrationTests.TestCasesAddresses.addressesTest;
import static service.proxy.IntegrationTests.TestCasesSuggestions.SampleSuggestions;
import static service.proxy.IntegrationTests.TestCasesSuggestions.suggestionsTest;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
public class CommonTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AddressesController addressesController;

    @Autowired
    private AddressesService addressesService;

    @Autowired
    private RequestsUtils requestsUtils;

    @Autowired
    private AddressesUtils addressesUtils;

    @Autowired
    private DaDataClient daDataClient;

    @Autowired
    public RequestRepository requestRepository;

    @Test
    public void NotNullClasses() {
        assertThat(addressesController).isNotNull();
        assertThat(addressesService).isNotNull();
        assertThat(requestsUtils).isNotNull();
        assertThat(addressesUtils).isNotNull();
        assertThat(daDataClient).isNotNull();
        assertThat(requestRepository).isNotNull();
    }

    @Test
    public void suggestVahtangova5A() {
        testSuggestions(SampleSuggestions.NSK_VAHTANGOVA_5A);
    }

    @Test
    public void suggestShluzovaia19() {
        testSuggestions(SampleSuggestions.NSK_SHLUZOVAIA_19);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = "classpath:db/migrations/truncateTables.sql")
    public void addressVahtangova5A() {
        fillData(requestRepository, SampleData.NSK_VAHTANGOVA_5A);
        testAddresses(SampleAddresses.NSK_VAHTANGOVA_5A);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = "classpath:db/migrations/truncateTables.sql")
    public void addressShluzovaia19() {
        fillData(requestRepository, SampleData.NSK_SHLUZOVAIA_19);
        testAddresses(SampleAddresses.NSK_SHLUZOVAIA_19);
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = "classpath:db/migrations/truncateTables.sql")
    public void clean() {
        fillData(requestRepository, SampleData.NSK_VAHTANGOVA_5A);
        fillData(requestRepository, SampleData.NSK_SHLUZOVAIA_19);
        requestsUtils.removeObsoleteRequests(2, 0, 0, 0, 0);
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


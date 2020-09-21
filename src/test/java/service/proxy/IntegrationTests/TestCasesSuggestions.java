package service.proxy.IntegrationTests;

import lombok.extern.java.Log;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@Log(topic = "TestCasesSuggestionsLog")
public class TestCasesSuggestions {

    public enum SampleSuggestions {

        NSK_VAHTANGOVA_5A("630058 Новосибирск Вахтангова 5А",
                "[\"630058, Новосибирская, г Новосибирск, ул Вахтангова, д 5А\"]"),
        NSK_SHLUZOVAIA_19("630058 Новосибирск Шлюзовая 19",
                "[\"630058, Новосибирская, г Новосибирск, ул Шлюзовая, д 19\"]");

        private final String request;
        private final String response;

        SampleSuggestions(final String request, final String response) {
            this.request = request;
            this.response = response;
        }

        public String getRequest() {
            return request;
        }

        public String getResponse() {
            return response;
        }
    }

    public static void suggestionsTest(TestRestTemplate restTemplate, int port, String request, String response) {

        log.info("request:\n" + request);

        String uri = String.format("http://localhost:%s/api/v1/address/%s", port, request);

        String responseBody = restTemplate.getForObject(uri, String.class);

        log.info("responseBody:\n" + responseBody);
        log.info("response:\n" + response);

        assertThat(responseBody).isEqualTo(response);
    }
}

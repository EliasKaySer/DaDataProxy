package service.proxy.IntegrationTests;

import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class TestCasesAddresses {

    public enum SampleAddresses {
        NSK_VAHTANGOVA_5A("city=Новосибирск&street=Вахтангова",
                "[{\"value\":\"630058, Новосибирская, г Новосибирск, ул Вахтангова, д 5А\",\"data\":{\"postal_code\":\"630058\",\"region\":\"Новосибирская\",\"city\":\"Новосибирск\",\"settlement\":null,\"street\":\"Вахтангова\",\"house\":\"5А\"}}]"),
        NSK_SHLUZOVAIA_19("city=Новосибирск&street=Шлюзовая",
                "[{\"value\":\"630058, Новосибирская, г Новосибирск, ул Шлюзовая, д 19\",\"data\":{\"postal_code\":\"630058\",\"region\":\"Новосибирская\",\"city\":\"Новосибирск\",\"settlement\":null,\"street\":\"Шлюзовая\",\"house\":\"19\"}}]");

        private final String request;
        private final String response;

        SampleAddresses(String request, String response) {
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

    public static void addressesTest(TestRestTemplate restTemplate, int port, String request, String response) {

        System.out.println("request: " + request);

        String uri = String.format("http://localhost:%s/api/v1/address/find?%s", port, request);

        String responseBody = restTemplate.postForObject(uri, null, String.class);

        System.out.println("responseBody: " + responseBody);

        assertThat(responseBody.hashCode()).isEqualTo(response.hashCode());
    }
}

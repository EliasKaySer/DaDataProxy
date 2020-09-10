package service.proxy.IntegrationTests;

import org.hamcrest.Matcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class TestCasesAddresses {

    public enum SampleAddresses {

        NSK_VAHTANGOVA_5A("630058 Новосибирск Вахтангова 5А",
                "[\"630058, Новосибирская, г Новосибирск, ул Вахтангова, д 5А\"]");

        private final String request;
        private final String response;

        SampleAddresses(final String request, final String response) {
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

    public static void SuccessTest(TestRestTemplate restTemplate, String url, String request, String response){

        System.out.println("request: " + request);

        String uri = String.format("%s%s", url, request);

        String responseBody = restTemplate.getForObject(uri, String.class);

        System.out.println("responseBody: " + responseBody);

        assertThat(responseBody).isEqualTo(response);
    }
}

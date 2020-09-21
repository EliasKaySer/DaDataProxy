package service.proxy.IntegrationTests;

import lombok.extern.java.Log;
import service.proxy.models.entityes.Address;
import service.proxy.models.entityes.Request;
import service.proxy.repositories.RequestRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Log(topic = "TestCaseFillDatabaseLog")
public class TestCaseFillDatabase {

    public enum SampleData {

        NSK_VAHTANGOVA_5A("630058 Новосибирск Вахтангова 5А",
                "630058", "Новосибирская",
                "Новосибирск", null,
                "Вахтангова", "5А"
        ),

        NSK_SHLUZOVAIA_19("630058 Новосибирск Шлюзовая 19",
                "630058", "Новосибирская",
                "Новосибирск", null,
                "Шлюзовая", "19"
        );

        private final String query;
        private final String postalcode;
        private final String region;
        private final String city;
        private final String settlement;
        private final String street;
        private final String house;

        SampleData(final String query, final String postalcode,
                   final String region, final String city,
                   final String settlement, final String street,
                   final String house
        ) {
            this.query = query;
            this.postalcode = postalcode;
            this.region = region;
            this.city = city;
            this.settlement = settlement;
            this.street = street;
            this.house = house;
        }

        public String getQuery() {
            return query;
        }

        public String getPostalcode() {
            return postalcode;
        }

        public String getRegion() {
            return region;
        }

        public String getCity() {
            return city;
        }

        public String getSettlement() {
            return settlement;
        }

        public String getStreet() {
            return street;
        }

        public String getHouse() {
            return house;
        }
    }

    public static void fillData(RequestRepository requestRepository,
                                String query, String postalcode,
                                String region, String city,
                                String settlement, String street,
                                String house) {

        Request request = new Request();
        Address address = new Address();

        address.setPostalcode(postalcode);
        address.setRegion(region);
        address.setCity(city);
        address.setSettlement(settlement);
        address.setStreet(street);
        address.setHouse(house);

        List<Address> addressesList = new ArrayList<>();
        addressesList.add(address);

        request.setAddresses(addressesList);

        request.setQuery(query);
        request.setCount(1);
        request.setDate(Instant.now());
        request.setResponses(addressesList.size());

        log.info("data before save:"
                + "\nrequest:\n" + request
                + "\naddress:\n" + address);

        requestRepository.save(request);

        log.info("data after save:"
                + "\nrequest:\n" + request
                + "\naddress:\n" + address);
    }

    public static void fillData(RequestRepository requestRepository,
                                SampleData sample) {

        Request request = new Request();
        Address address = new Address();

        address.setPostalcode(sample.postalcode);
        address.setRegion(sample.region);
        address.setCity(sample.city);
        address.setSettlement(sample.settlement);
        address.setStreet(sample.street);
        address.setHouse(sample.house);

        List<Address> addressesList = new ArrayList<>();
        addressesList.add(address);

        request.setAddresses(addressesList);

        request.setQuery(sample.query);
        request.setCount(1);
        request.setDate(Instant.now());
        request.setResponses(addressesList.size());

        log.info("data before save:"
                + "\nrequest:\n" + request
                + "\naddress:\n" + address);

        requestRepository.save(request);

        log.info("data after save:"
                + "\nrequest:\n" + request
                + "\naddress:\n" + address);
    }
}

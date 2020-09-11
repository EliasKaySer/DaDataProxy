package service.proxy.services.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import service.proxy.models.entityes.Address;
import service.proxy.models.entityes.Request;
import service.proxy.repositories.AddressRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AddressesUtils {

    private final AddressRepository addressRepository;

    public String getAddressValue(Address address) {
        String value = StringUtils.hasText(address.getPostalcode()) ? address.getPostalcode() : "";
        value += StringUtils.hasText(address.getRegion())
                ? (String.format("%s%s", StringUtils.hasText(value) ? ", " : "", address.getRegion()))
                : "";
        value += StringUtils.hasText(address.getCity())
                ? (String.format("%sг %s", StringUtils.hasText(value) ? ", " : "", address.getCity()))
                : "";
        value += StringUtils.hasText(address.getSettlement())
                ? (String.format("%s%s", StringUtils.hasText(value) ? ", " : "", address.getSettlement()))
                : "";
        value += StringUtils.hasText(address.getStreet())
                ? (String.format("%sул %s", StringUtils.hasText(value) ? ", " : "", address.getStreet()))
                : "";
        value += StringUtils.hasText(address.getHouse())
                ? (String.format("%sд %s", StringUtils.hasText(value) ? ", " : "", address.getHouse()))
                : "";
        return value;
    }

    public List<String> getAddressesAsStingList(List<Address> addresses) {
        return addresses
                .stream()
                .map(this::getAddressValue)
                .collect(Collectors.toList());
    }

    public List<Address> getAddresses(String region, String city, String settlement, String street) {
        List<Address> entityes = new ArrayList<>();

        if (StringUtils.hasText(region)) {
            entityes = addressRepository.findAllByRegionLikeIgnoreCase(region);
        }

        if (StringUtils.hasText(city)) {
            if (entityes.size() > 0) {
                entityes = entityes.stream().filter(a -> a.getCity().toUpperCase().equals(city.toUpperCase()))
                        .collect(Collectors.toList());
            } else {
                entityes = addressRepository.findAllByCityLikeIgnoreCase(city);
            }
        }

        if (StringUtils.hasText(settlement)) {
            if (entityes.size() > 0) {
                entityes = entityes.stream().filter(a -> a.getSettlement().toUpperCase().equals(settlement.toUpperCase()))
                        .collect(Collectors.toList());
            } else {
                entityes = addressRepository.findAllBySettlementLikeIgnoreCase(settlement);
            }
        }

        if (StringUtils.hasText(street)) {
            if (entityes.size() > 0) {
                entityes = entityes.stream().filter(a -> a.getStreet().toUpperCase().equals(street.toUpperCase()))
                        .collect(Collectors.toList());
            } else {
                entityes = addressRepository.findAllByStreetLikeIgnoreCase(street);
            }
        }
        return entityes;
    }

    public List<Address> getAddresses(Address address) {
        return addressRepository.findByPostalcodeAndRegionAndCityAndSettlementAndStreetAndHouse(
                address.getPostalcode(), address.getRegion(), address.getCity(),
                address.getSettlement(), address.getStreet(), address.getHouse());
    }

    public boolean equalsAddresses(Address address, Address persistAddress) {
        int addressHashCode = (address.getPostalcode() != null ? address.getPostalcode().hashCode() : 0)
                + (address.getRegion() != null ? address.getRegion().hashCode() : 0)
                + (address.getCity() != null ? address.getCity().hashCode() : 0)
                + (address.getSettlement() != null ? address.getSettlement().hashCode() : 0)
                + (address.getStreet() != null ? address.getStreet().hashCode() : 0)
                + (address.getHouse() != null ? address.getHouse().hashCode() : 0);
        int persistAddressHashCode = (persistAddress.getPostalcode() != null ? persistAddress.getPostalcode().hashCode() : 0)
                + (persistAddress.getRegion() != null ? persistAddress.getRegion().hashCode() : 0)
                + (persistAddress.getCity() != null ? persistAddress.getCity().hashCode() : 0)
                + (persistAddress.getSettlement() != null ? persistAddress.getSettlement().hashCode() : 0)
                + (persistAddress.getStreet() != null ? persistAddress.getStreet().hashCode() : 0)
                + (persistAddress.getHouse() != null ? persistAddress.getHouse().hashCode() : 0);
        return addressHashCode == persistAddressHashCode;
    }

    @Transactional
    public List<Address> getUpdatedRelatedAddressesList(Request request, List<Address> addresses) {
        List<Address> persistedAddresses = addresses.stream()
                .map(this::getAddresses)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Address> persistAddressesToRemove = persistedAddresses.stream()
                .filter(a -> a.getRequests().stream().anyMatch(r -> r.getId() == request.getId())
                        && a.getRequests().size() == 1)
                .collect(Collectors.toList());
        List<Address> persistAddressesWithChild = persistedAddresses.stream()
                .filter(a -> a.getRequests().size() > 1)
                .collect(Collectors.toList());

        addressRepository.deleteAll(persistAddressesToRemove);

        persistAddressesWithChild.forEach(a -> a.getRequests().remove(request));
        addressRepository.saveAll(persistAddressesWithChild);

        for (Address persistedAddress :
                persistedAddresses) {
            addresses.removeIf(a -> equalsAddresses(a, persistedAddress));
        }

        addresses.addAll(persistedAddresses);
        return addresses;
    }

    public void removeObsoleteAddresses(List<UUID> requestsIds, List<Address> addresses) {
        for (Address address : addresses) {
            if (address.getRequests().stream()
                    .filter(a -> !requestsIds.contains(a.getId()))
                    .collect(Collectors.toList()).size() == 0) {
                addressRepository.delete(address);
            }
        }
    }
}

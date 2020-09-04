package service.proxy.addresses.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import service.proxy.addresses.model.entity.Address;
import service.proxy.addresses.repository.AddressRepository;

@Api
@RestController
@RequestMapping("/api/v1/proxy")
public class AddressesController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Address> getAllAddress() {
        return addressRepository.findAll();
    }

}

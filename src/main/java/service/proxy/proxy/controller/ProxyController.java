package service.proxy.proxy.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import service.proxy.proxy.model.entity.Address;
import service.proxy.proxy.repository.ProxyRepository;

@Api
@RestController
@RequestMapping("/api/v1/proxy")
public class ProxyController {

    @Autowired
    private ProxyRepository repository;

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Address> getAllAddress() {
        return repository.findAll();
    }

}

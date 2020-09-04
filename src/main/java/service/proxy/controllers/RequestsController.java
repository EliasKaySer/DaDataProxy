package service.proxy.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.proxy.models.entity.Request;
import service.proxy.services.implementes.AddressesService;

@Api
@RestController
@RequestMapping("/api/v1/request")
public class RequestsController {

    @Autowired
    private AddressesService addressesService;

    @ApiOperation(
            value = "Все запросы",
            notes = "Возвращается список запросов из БД"
    )
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Request> getAllRequest() { return addressesService.getAllRequest(); }

    @ApiOperation(
            value = "Удалить не нужные уставшие запросы",
            notes = "Возвращается список запросов из БД"
    )
    @RequestMapping(value = "/clean", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Request> cleanReqest() { return addressesService.getCleanedRequests(); }
}

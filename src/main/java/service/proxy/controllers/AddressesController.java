package service.proxy.controllers;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.proxy.models.entity.Address;
import service.proxy.models.entity.Request;
import service.proxy.models.transport.AddressDto;
import service.proxy.models.transport.AddressSuggestionDto;
import service.proxy.services.implementes.AddressesService;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/v1/address")
public class AddressesController {

    @Autowired
    private AddressesService addressesService;

    @ApiOperation(
            value = "Все адреса",
            notes = "Возвращается список адресов из БД"
    )
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Address> getAllAddress() {
        return addressesService.getAllAddress();
    }

    @ApiOperation(
            value = "Подсказки по адресу",
            notes = "Возвращается список объектов адреса"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Запрос успешно обработан"),
            @ApiResponse(code = 400, message = "Некорректный запрос (невалидный JSON или XML)"),
            @ApiResponse(code = 401, message = "В запросе отсутствует API-ключ"),
            @ApiResponse(code = 403, message = "В запросе указан несуществующий API-ключ\nИли не подтверждена почта\nИли исчерпан дневной лимит по количеству запросов"),
            @ApiResponse(code = 405, message = "Запрос сделан с методом, отличным от POST"),
            @ApiResponse(code = 413, message = "Слишком большая длина запроса или слишком много условий"),
            @ApiResponse(code = 429, message = "Слишком много запросов в секунду или новых соединений в минуту"),
            @ApiResponse(code = 500, message = "Произошла внутренняя ошибка сервиса"),
    })
    @RequestMapping(value = "/{query}", method = RequestMethod.GET)
    private @ResponseBody List<String> getAddresses(
            @ApiParam(value = "Текст запроса", required = true, defaultValue = "", type = "String",
                    example = "Мусы Джа 9 630055") @PathVariable String query
    ) {
        return addressesService.getAddresses(query, 10, "ru");
    }

    @ApiOperation(
            value = "Подсказки по адресу",
            notes = "Возвращается список объектов адреса"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Запрос успешно обработан"),
            @ApiResponse(code = 400, message = "Некорректный запрос (невалидный JSON или XML)"),
            @ApiResponse(code = 401, message = "В запросе отсутствует API-ключ"),
            @ApiResponse(code = 403, message = "В запросе указан несуществующий API-ключ\nИли не подтверждена почта\nИли исчерпан дневной лимит по количеству запросов"),
            @ApiResponse(code = 405, message = "Запрос сделан с методом, отличным от POST"),
            @ApiResponse(code = 413, message = "Слишком большая длина запроса или слишком много условий"),
            @ApiResponse(code = 429, message = "Слишком много запросов в секунду или новых соединений в минуту"),
            @ApiResponse(code = 500, message = "Произошла внутренняя ошибка сервиса"),
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    private @ResponseBody List<String> getAddresses(
            @ApiParam(value = "Текст запроса", required = true, defaultValue = "", type = "String",
                    example = "москва хабар") @RequestParam String query,
            @ApiParam(value = "Количество результатов (максимум — 20)", required = false, defaultValue = "10", type = "Integer",
                    example = "10") @RequestParam Integer count,
            @ApiParam(value = "На каком языке вернуть результат (ru / en)", required = false, defaultValue = "ru", type = "String",
                    example = "ru") @RequestParam String language) {
        return addressesService.getAddresses(query, count, language);
    }
}

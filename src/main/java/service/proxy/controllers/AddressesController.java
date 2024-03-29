package service.proxy.controllers;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import service.proxy.models.converters.AddressMaper;
import service.proxy.models.transports.AddressDto;
import service.proxy.services.implementes.AddressesService;

import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressesController {

    private final AddressesService addressesService;

    private final AddressMaper addressMaper;

    @ApiOperation(
            value = "Очистка данных",
            notes = "Очистка данных от устаревших запросов"
    )
    @RequestMapping(value = "/clean", method = RequestMethod.GET)
    public void clean() {
        addressesService.clean();
    }

    @ApiOperation(
            value = "Все адреса",
            notes = "Возвращается список адресов из БД"
    )
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    List<AddressDto> getAllAddresses() {
        return addressesService.getAllAddresses()
                .stream()
                .map(addressMaper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(
            value = "Поиск адресов",
            notes = "Возвращается список адресов из БД"
    )
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public @ResponseBody
    List<AddressDto> getAddresses(
            @ApiParam(value = "Регион", defaultValue = "", type = "String",
                    example = "") @RequestParam(required = false) String region,
            @ApiParam(value = "Город", defaultValue = "", type = "String",
                    example = "") @RequestParam(required = false) String city,
            @ApiParam(value = "Поселок", defaultValue = "", type = "String",
                    example = "") @RequestParam(required = false) String settlement,
            @ApiParam(value = "Улица", defaultValue = "", type = "String",
                    example = "") @RequestParam(required = false) String street) {
        return addressesService.getAddresses(region, city, settlement, street)
                .stream()
                .map(addressMaper::toDto)
                .collect(Collectors.toList());
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
    public @ResponseBody
    List<String> getSuggestions(
            @ApiParam(value = "Текст запроса", defaultValue = "", type = "String",
                    example = "630058 Новосибирск Вахтангова 5") @PathVariable(required = true) String query
    ) {
        return addressesService.getSuggestions(query, 10, "ru").stream()
                .map(addressMaper::toDto)
                .map(AddressDto::getValue)
                .collect(Collectors.toList());
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
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody
    List<String> getSuggestions(
            @ApiParam(value = "Текст запроса", defaultValue = "", type = "String",
                    example = "630058 Новосибирск Вахтангова 5") @RequestParam(required = true) String query,
            @ApiParam(value = "Количество результатов (максимум — 20)", defaultValue = "10", type = "Integer",
                    example = "10") @RequestParam(required = false, defaultValue = "10") Integer count,
            @ApiParam(value = "На каком языке вернуть результат (ru / en)", defaultValue = "ru", type = "String",
                    example = "ru") @RequestParam(required = false, defaultValue = "ru") String language) {
        return addressesService.getSuggestions(query, count, language).stream()
                .map(addressMaper::toDto)
                .map(AddressDto::getValue)
                .collect(Collectors.toList());
    }
}

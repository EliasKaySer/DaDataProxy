package service.proxy.dadata.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.proxy.addresses.model.entity.Address;
import service.proxy.dadata.model.transport.DaDataResponseDTO;
import service.proxy.dadata.model.transport.clean.AddressCleanDTO;
import service.proxy.dadata.service.DaDataService;

import java.util.List;

@Api
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
@RestController
@RequestMapping("/api/v1/dadata")
public class DaDataController {

    @Autowired
    private DaDataService service;

    @GetMapping("{query}")
    private List<AddressCleanDTO> getAddresses(
            @ApiParam(
                    value = "Текст запроса",
                    required = true,
                    defaultValue = "",
                    type = "String",
//                    example = "шлюзовая 19 630058"
                    example = "Мусы Джа 9 630055"
            )
            @PathVariable
                    String query) {




        return service.getAddresses(query, 10, "ru");
    }

//    @PostMapping
//    private DaDataResponseDTO getAddresses(
//            @ApiParam(
//                    value = "Текст запроса",
//                    required = true,
//                    defaultValue = "",
//                    type = "String",
//                    example = "москва хабар"
//            )
//            @RequestBody
//                    String query,
//
//            @ApiParam(
//                    value = "Количество результатов (максимум — 20)",
//                    required = false,
//                    defaultValue = "10",
//                    type = "Integer",
//                    example = "10"
//            )
//            @RequestBody
//                    Integer count,
//
//            @ApiParam(
//                    value = "На каком языке вернуть результат (ru / en)",
//                    required = false,
//                    defaultValue = "ru",
//                    type = "String",
//                    example = "ru"
//            )
//            @RequestBody
//                    String language) {
//
//
//        return service.Addresses(query, count, language);
//    }
}

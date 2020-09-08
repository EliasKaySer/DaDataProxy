package service.proxy.models.transports.dadata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(
        value = "Данные адреса (Полные)",
        description = "Сущность данных адреса из ответа DaData")
@Data
public class DaDataSuggestionsDataDto {

    @ApiModelProperty(value = "Индекс")
    private String postal_code;
    @ApiModelProperty(value = "Страна")
    private String country;
    @ApiModelProperty(value = "ISO-код страны (двухсимвольный)")
    private String country_iso_code;
    @ApiModelProperty(value = "Федеральный округ")
    private String federal_district;

    @ApiModelProperty(value = "Код ФИАС региона")
    private String region_fias_id;
    @ApiModelProperty(value = "Код КЛАДР региона")
    private String region_kladr_id;
    @ApiModelProperty(value = "ISO-код региона")
    private String region_iso_code;
    @ApiModelProperty(value = "Регион с типом")
    private String region_with_type;
    @ApiModelProperty(value = "Тип региона (сокращенный)")
    private String region_type;
    @ApiModelProperty(value = "Тип региона")
    private String region_type_full;
    @ApiModelProperty(value = "Регион")
    private String region;

    @ApiModelProperty(value = "Код ФИАС района в регионе")
    private String area_fias_id;
    @ApiModelProperty(value = "Код КЛАДР района в регионе")
    private String area_kladr_id;
    @ApiModelProperty(value = "Район в регионе с типом")
    private String area_with_type;
    @ApiModelProperty(value = "Тип района в регионе (сокращенный)")
    private String area_type;
    @ApiModelProperty(value = "Тип района в регионе")
    private String area_type_full;
    @ApiModelProperty(value = "Район в регионе")
    private String area;

    @ApiModelProperty(value = "Код ФИАС города")
    private String city_fias_id;
    @ApiModelProperty(value = "Код КЛАДР города")
    private String city_kladr_id;
    @ApiModelProperty(value = "Город с типом")
    private String city_with_type;
    @ApiModelProperty(value = "Тип города (сокращенный)")
    private String city_type;
    @ApiModelProperty(value = "Тип города")
    private String city_type_full;
    @ApiModelProperty(value = "Город")
    private String city;

    @ApiModelProperty(value = "Код ФИАС района города (заполняется, только если район есть в ФИАС)")
    private String city_district_fias_id;
    @ApiModelProperty(value = "Код КЛАДР района города (не заполняется)")
    private String city_district_kladr_id;
    @ApiModelProperty(value = "Район города с типом")
    private String city_district_with_type;
    @ApiModelProperty(value = "Тип района города (сокращенный)")
    private String city_district_type;
    @ApiModelProperty(value = "Тип района города")
    private String city_district_type_full;
    @ApiModelProperty(value = "Район города")
    private String city_district;

    @ApiModelProperty(value = "Код ФИАС нас. пункта")
    private String settlement_fias_id;
    @ApiModelProperty(value = "Код КЛАДР нас. пункта")
    private String settlement_kladr_id;
    @ApiModelProperty(value = "Населенный пункт с типом")
    private String settlement_with_type;
    @ApiModelProperty(value = "Тип населенного пункта (сокращенный)")
    private String settlement_type;
    @ApiModelProperty(value = "Тип населенного пункта")
    private String settlement_type_full;
    @ApiModelProperty(value = "Населенный пункт")
    private String settlement;

    @ApiModelProperty(value = "Код ФИАС улицы")
    private String street_fias_id;
    @ApiModelProperty(value = "Код КЛАДР улицы")
    private String street_kladr_id;
    @ApiModelProperty(value = "Улица с типом")
    private String street_with_type;
    @ApiModelProperty(value = "Тип улицы (сокращенный)")
    private String street_type;
    @ApiModelProperty(value = "Тип улицы")
    private String street_type_full;
    @ApiModelProperty(value = "Улица")
    private String street;

    @ApiModelProperty(value = "Код ФИАС дома")
    private String house_fias_id;
    @ApiModelProperty(value = "Код КЛАДР дома")
    private String house_kladr_id;
    @ApiModelProperty(value = "Тип дома (сокращенный)")
    private String house_type;
    @ApiModelProperty(value = "Тип дома")
    private String house_type_full;
    @ApiModelProperty(value = "Дом")
    private String house;


    @ApiModelProperty(value = "Тип корпуса/строения (сокращенный)")
    private String block_type;
    @ApiModelProperty(value = "Тип корпуса/строения")
    private String block_type_full;
    @ApiModelProperty(value = "Корпус/строение")
    private String block;

    @ApiModelProperty(value = "Тип квартиры (сокращенный)")
    private String flat_type;
    @ApiModelProperty(value = "Тип квартиры")
    private String flat_type_full;
    @ApiModelProperty(value = "Квартира")
    private String flat;

    @ApiModelProperty(value = "Абонентский ящик")
    private String postal_box;

    @ApiModelProperty(value = "Код ФИАС для России:\n" +
            "HOUSE.HOUSEGUID, если дом найден в ФИАС по точному совпадению;\n" +
            "ADDROBJ.AOGUID в противном случае.\n" +
            "Идентификатор OpenStreetMap для Белоруссии.\n" +
            "Для остальных стран — не заполняется.")
    private String fias_id;

    @ApiModelProperty(value = "Уровень детализации, до которого адрес найден в ФИАС:\n" +
            "0 — страна\n" +
            "1 — регион\n" +
            "3 — район\n" +
            "4 — город\n" +
            "5 — район города\n" +
            "6 — населенный пункт\n" +
            "7 — улица\n" +
            "8 — дом\n" +
            "65 — планировочная структура\n" +
            "-1 — иностранный или пустой\n")
    private String fias_level;

    @ApiModelProperty(value = "Код КЛАДР")
    private String kladr_id;
    @ApiModelProperty(value = "Идентификатор объекта в базе GeoNames. Для российских адресов не заполняется.")
    private String geoname_id;
    @ApiModelProperty(value = "Признак центра района или региона:\n" +
            "1 — центр района (Московская обл, Одинцовский р-н, г Одинцово)\n" +
            "2 — центр региона (Новосибирская обл, г Новосибирск)\n" +
            "3 — центр района и региона (Томская обл, г Томск)\n" +
            "4 — центральный район региона (Тюменская обл, Тюменский р-н)\n" +
            "0 — ничего из перечисленного (Московская обл, г Балашиха)\n")
    private String capital_marker;
    @ApiModelProperty(value = "Код ОКАТО")
    private String okato;
    @ApiModelProperty(value = "Код ОКТМО")
    private String oktmo;
    @ApiModelProperty(value = "Код ИФНС для физических лиц")
    private String tax_office;
    @ApiModelProperty(value = "Код ИФНС для организаций")
    private String tax_office_legal;
    @ApiModelProperty(value = "Список исторических названий объекта нижнего уровня.\n" +
            "Если подсказка до улицы — это прошлые названия этой улицы, если до города — города.")
    private String[] history_values;
}

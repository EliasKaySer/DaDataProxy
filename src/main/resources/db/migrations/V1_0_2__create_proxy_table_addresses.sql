create table addresses
(
    id         uuid         not null default uuid_generate_v4()
        constraint addresses_pkey
            primary key,
    city       varchar(255),
    house      varchar(255),
    postalcode varchar(255),
    region     varchar(255),
    settlement varchar(255),
    street     varchar(255),
    constraint addresses_postalcode_region_city_settlement_street_house_uk
        unique (postalcode, region, city, settlement, street, house)
);

alter table addresses
    owner to proxy;
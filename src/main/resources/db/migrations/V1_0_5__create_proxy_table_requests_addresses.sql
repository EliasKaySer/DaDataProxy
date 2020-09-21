create table requests_addresses
(
    request_id uuid not null
        constraint requests_addresses_request_id_fkey
            references requests,
    address_id uuid not null
        constraint requests_addresses_address_id_fkey
            references addresses,
    constraint requests_addresses_request_id_address_id_uk
        unique (request_id, address_id)
);

alter table requests_addresses
    owner to proxy;
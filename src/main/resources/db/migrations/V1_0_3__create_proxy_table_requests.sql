create table requests
(
    id        uuid                     not null default uuid_generate_v4()
        constraint requests_pkey
            primary key,
    count     integer                  not null default 0,
    date      timestamp with time zone not null,
    query     varchar(255)             not null
        constraint requests_query_uk
            unique,
    responses integer                  not null
);

alter table requests
    owner to proxy;
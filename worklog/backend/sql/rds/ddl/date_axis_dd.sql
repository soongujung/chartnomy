create table date_axis_dd
(
    yyyy     text     null,
    mm       text     null,
    dd       text     null,
    yyyymmdd datetime not null
        primary key
);

create index date_axis_dd_yyyymmdd_index
    on date_axis_dd (yyyymmdd);


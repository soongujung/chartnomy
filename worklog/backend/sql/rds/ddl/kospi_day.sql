create table kospi_day
(
    STAT_NAME  text     null,
    STAT_CODE  text     null,
    ITEM_CODE1 text     null,
    ITEM_CODE2 text     null,
    ITEM_CODE3 text     null,
    ITEM_NAME1 text     null,
    ITEM_NAME2 text     null,
    ITEM_NAME3 text     null,
    DATA_VALUE text     null,
    TIME       datetime null,
    constraint kospi_day_pk
        unique (TIME)
);

create index kospi_day_TIME_index
    on kospi_day (TIME);


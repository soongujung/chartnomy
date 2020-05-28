create table exchange_rate_dollar_day
(
    STAT_NAME  text     null,
    STAT_CODE  text     null,
    ITEM_CODE1 text     null,
    ITEM_CODE2 text     null,
    ITEM_CODE3 text     null,
    ITEM_NAME1 text     null,
    ITEM_NAME2 text     null,
    ITEM_NAME3 text     null,
    DATA_VALUE double   null,
    TIME       datetime not null
        primary key
);

create index exchange_rate_dollar_day_TIME_index
    on exchange_rate_dollar_day (TIME);


create table loan_corporate_month
(
    UNIT_NAME  text     null,
    STAT_NAME  text     null,
    STAT_CODE  text     null,
    ITEM_CODE1 text     null,
    ITEM_CODE2 text     null,
    ITEM_CODE3 text     null,
    ITEM_NAME1 text     null,
    ITEM_NAME2 text     null,
    ITEM_NAME3 text     null,
    DATA_VALUE double   null,
    TIME       datetime null,
    constraint loan_corporate_month_pk
        unique (TIME)
);

create index loan_corporate_month_TIME_index
    on loan_corporate_month (TIME);


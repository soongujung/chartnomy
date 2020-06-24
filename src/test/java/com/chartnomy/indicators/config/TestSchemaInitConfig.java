package com.chartnomy.indicators.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

/**
 * Schema DDL/Insert 설정
 */
@Component
@PropertySource(value = {"classpath:test-application.properties"})
@ActiveProfiles("testdocker")
public class TestSchemaInitConfig {

	@Autowired
	ApplicationContext context;

	@Value("${test.database.mariadb.initialized}")
	private String isInitialized;

	@EventListener({ContextRefreshedEvent.class})
	public void applicationStarted(){
		if("false".equals(isInitialized)){
			initDatabase();
		}
	}

	private void initDatabase() {
		createSchema();
		insertData();
	}

	@Sql(scripts = {
		"/datasets/mariadb/ddl/schema.sql"
	}, config = @SqlConfig(dataSource = "dataSource"))
	private void createSchema() {
	}

	@Sql(scripts = {
		"/datasets/mariadb/insert/mariadb_insert_date_axis_dd.sql",
		"/datasets/mariadb/insert/mariadb_insert_kospi_day.sql",
		"/datasets/mariadb/insert/mariadb_insert_loan_corporate_month.sql",
		"/datasets/mariadb/insert/mariadb_insert_loan_household_month.sql",
		"/datasets/mariadb/insert/mariadb_insert_loan_rate_kor.sql",
		"/datasets/mariadb/insert/mariadb_insert_loan_rate_usa.sql",
		"/datasets/mariadb/insert/mariadb_insert_exchange_dollar_day.sql",
	}, config = @SqlConfig(dataSource = "dataSource"))
	private void insertData() {
	}

}

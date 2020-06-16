package com.chartnomy.indicators.testdocker.api.web.trending.index.repository;

import com.chartnomy.indicators.api.web.trending.index.QTrendingIndexRepository;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexPriceDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexRateDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@SpringBootTest
@ActiveProfiles("testdocker")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class TrendingIndexRepositoryTest {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private QTrendingIndexRepository trendingRepository;

	private JPAQueryFactory queryFactory;

	@BeforeAll
	void setupApplication(){
		queryFactory = new JPAQueryFactory(entityManager);
	}

	@Disabled
	@DisplayName("DB 셋업")
	@Test
	@Order(1)
	@Sql(scripts = {
		"/datasets/mariadb/ddl/schema.sql",
		"/datasets/mariadb/insert/mariadb_insert_date_axis_dd.sql",
		"/datasets/mariadb/insert/mariadb_insert_kospi_day.sql",
		"/datasets/mariadb/insert/mariadb_insert_loan_corporate_month.sql",
		"/datasets/mariadb/insert/mariadb_insert_loan_household_month.sql",
		"/datasets/mariadb/insert/mariadb_insert_loan_rate_kor.sql",
		"/datasets/mariadb/insert/mariadb_insert_loan_rate_usa.sql",
	}, config = @SqlConfig(dataSource = "dataSource"))
	void setupSchema(){

	}

	@Test
	@Order(2)
	void testGetKospiResult(){

		List<IndexPriceDto> kospiResult = trendingRepository.getKospiResult(
			getDateParameter().getFromDate(),
			getDateParameter().getToDate()
		);
	}

	@Test
	@Order(3)
	void testGetExchangeRateDollar(){
		List<IndexPriceDto> exchangeRateDollar = trendingRepository.getExchangeRateDollar(
			getDateParameter().getFromDate(),
			getDateParameter().getToDate()
		);
	}

	@Test
	@Order(4)
	void testGetLoanRateKr(){
		List<IndexRateDto> loanKrRate = trendingRepository.getLoanKrRate(
			getDateParameter().getFromDate(),
			getDateParameter().getToDate()
		);
	}

	@Test
	@Order(5)
	void testGetLoanRateUs(){
		List<IndexRateDto> loanUsRate = trendingRepository.getLoanUsRate(
			getDateParameter().getFromDate(),
			getDateParameter().getToDate()
		);
	}

	private TestDateParameter getDateParameter(){
		return null;
	}

	class TestDateParameter{
		LocalDateTime fromDate;
		LocalDateTime toDate;

		public LocalDateTime getFromDate() {
			return fromDate;
		}

		public void setFromDate(LocalDateTime fromDate) {
			this.fromDate = fromDate;
		}

		public LocalDateTime getToDate() {
			return toDate;
		}

		public void setToDate(LocalDateTime toDate) {
			this.toDate = toDate;
		}
	}
}

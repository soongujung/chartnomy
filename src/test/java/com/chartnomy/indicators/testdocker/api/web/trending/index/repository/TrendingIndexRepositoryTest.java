package com.chartnomy.indicators.testdocker.api.web.trending.index.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.chartnomy.indicators.api.web.trending.index.QTrendingIndexRepository;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexDateDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexPriceDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexRateDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
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

	@Test
	@Order(6)
	void testDateResult(){
//		String fromStr = "20200101000001"; // 이렇게 하면 1월 2일 부터 조회된다.
		String fromStr 	= "20200101000000";
		String toStr 	= "20201231000000";

		String formatter = "yyyyMMddHHmmss";
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(formatter);

		LocalDateTime fromDate = LocalDateTime.parse(fromStr, dateFormatter);
		LocalDateTime toDate = LocalDateTime.parse(toStr, dateFormatter);

		List<IndexDateDto> dateResult = trendingRepository.getDateResult(
			fromDate, toDate
		);

		int dateResultSize = dateResult.size();
//		System.out.println("size >>> " + dateResultSize);
		assertThat(dateResultSize).isEqualTo(366); // ?

		IndexDateDto startDate = dateResult.get(0);
		IndexDateDto endDate = dateResult.get(dateResultSize - 1);

//		System.out.println("fromDate >>> " + dateResult.get(0) + ", " + fromDate);
//		System.out.println("toDate >>> " + dateResult.get(dateResultSize -1) + ", " + toDate);

		assertThat(startDate.getDate()).isEqualTo(fromDate);
		assertThat(endDate.getDate()).isEqualTo(toDate);
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

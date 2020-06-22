package com.chartnomy.indicators.testdocker.api.web.trending.index.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.chartnomy.indicators.api.web.trending.index.QTrendingIndexRepository;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexDateDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexPriceDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexRateDto;
import com.chartnomy.indicators.config.TestSchemaInitConfig;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(TestSchemaInitConfig.class)
@SpringBootTest
//@ExtendWith(SpringExtension.class)
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

		List<IndexDateDto> dateResult = trendingRepository.getDateSeries(fromDate, toDate);

		int dateResultSize = dateResult.size();
		assertThat(dateResultSize).isEqualTo(366); // 2020년은 윤년 이기 때문에 366일

		IndexDateDto startDate = dateResult.get(0);
		IndexDateDto endDate = dateResult.get(dateResultSize - 1);

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

package com.chartnomy.indicators.testdocker;

import static com.chartnomy.indicators.domain.axis.entity.QDateAxisDd.dateAxisDd;
import static com.chartnomy.indicators.domain.kospi.entity.QKospi.kospi;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

import com.chartnomy.indicators.domain.axis.entity.DateAxisDd;
import com.chartnomy.indicators.domain.axis.entity.QDateAxisDd;
import com.chartnomy.indicators.domain.kospi.entity.Kospi;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("testdocker")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class ConnectionTest{

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private DataSource dataSource;

	private JPAQueryFactory queryFactory;

	@BeforeAll
	void setupApplication(){
		queryFactory = new JPAQueryFactory(entityManager);
	}

	@DisplayName("DB 셋업")
	@Test
	@Order(1)
	@Sql(scripts = {
		"/datasets/mariadb/ddl/schema.sql",
		"/datasets/mariadb/insert/mariadb_insert_date_axis_dd.sql",
		"/datasets/mariadb/insert/mariadb_insert_kospi_day.sql"
	}, config = @SqlConfig(dataSource = "dataSource"))
	void setupSchema(){
	}

	@Test
	@Order(2)
	void test_connection(){
		List<Kospi> kospiList = queryFactory.selectFrom(kospi)
			.leftJoin(dateAxisDd)
			.on(dateAxisDd.date.eq(kospi.time))
			.fetch();

		Comparator<Kospi> kospiComp = Comparator.comparingDouble(Kospi::getPrice);

		// YYYYMM 별 최대값 구하기
		Map<String, Optional<Kospi>> collectedData = kospiList.stream()
			.map(k -> {
				if (k.getPrice() == null) {
					k.setPrice(0.00);
				}
				return k;
			})
//			.collect(groupingBy(Kospi::getYYYYMM, maxBy(comparingDouble(Kospi::getPrice))));
			.collect(groupingBy(Kospi::getYYYYMM, maxBy(kospiComp)));

		// 데이터 Sorting
		Map<String, Kospi> result = new LinkedHashMap<>();

		collectedData.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.forEachOrdered(x->result.put(x.getKey(), x.getValue().get()));
	}
}

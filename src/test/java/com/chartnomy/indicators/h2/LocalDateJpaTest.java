package com.chartnomy.indicators.h2;

import com.chartnomy.indicators.domain.axis.entity.DateAxis;
import com.chartnomy.indicators.domain.axis.entity.QDateAxis;
import com.chartnomy.indicators.domain.kospi.entity.Kospi;
import com.chartnomy.indicators.domain.kospi.entity.QKospi;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


// ExtendWith 는 JUnit4의 RunWith(SpringRunner.class) 와 비슷한 역할
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Commit
public class LocalDateJpaTest {

	@Autowired
	private EntityManager entityManager;

	private JPAQueryFactory queryFactory;

	private final static DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
	private final static DateTimeFormatter yyyy = DateTimeFormatter.ofPattern("yyyy");
	private final static DateTimeFormatter MM = DateTimeFormatter.ofPattern("MM");
	private final static DateTimeFormatter dd = DateTimeFormatter.ofPattern("dd");

	@BeforeEach
	public void beforeTest(){
		queryFactory = new JPAQueryFactory(entityManager);
		//"yyyy-MM-dd"
		DateAxis dateAxis1 = new DateAxis();
		LocalDate date = LocalDate.now();
		dateAxis1.setDate(date);
		dateAxis1.setDayOfMonth(date.format(yyyy));
		dateAxis1.setMonth(date.format(MM));
		dateAxis1.setYear(date.format(dd));

		entityManager.persist(dateAxis1);

		// 2020/04/01 ~ 2020/04-30 까지 데이터 insert (localdate iteration)
		Kospi kospi = new Kospi();
		kospi.setItemCode1("aaaa");
		kospi.setItemCode2("bbbb");
		kospi.setItemCode3("cccc");
		kospi.setItemName1("1111");
		kospi.setItemName1("2222");
		kospi.setItemName1("3333");
		kospi.setStatCode("----");
		kospi.setStatName("----");
		kospi.setPrice("11111");
		kospi.setTime(LocalDate.now());

		entityManager.persist(kospi);
	}

	@Test
	public void test1(){
		QDateAxis axis = QDateAxis.dateAxis;
		QKospi kospi = QKospi.kospi;

		List<Tuple> fetch = queryFactory.select(axis.date, kospi.price)
			.from(kospi)
			.join(axis)
			.on(axis.date.eq(kospi.time))
			.fetch();

		fetch.stream().forEach(System.out::println);
	}
}

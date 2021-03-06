package com.chartnomy.indicators.h2.etc;

import com.chartnomy.indicators.domain.axis.entity.DateAxis;
import com.chartnomy.indicators.domain.kospi.entity.Kospi;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


// ExtendWith 는 JUnit4의 RunWith(SpringRunner.class) 와 비슷한 역할
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
//@Transactional
//@Commit
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
		kospi.setPrice(111.0);
//		kospi.setTime(LocalDate.now());

		entityManager.persist(kospi);
	}

}

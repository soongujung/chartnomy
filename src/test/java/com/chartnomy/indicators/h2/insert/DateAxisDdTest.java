package com.chartnomy.indicators.h2.insert;

import com.chartnomy.indicators.domain.axis.entity.DateAxisDd;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Commit
public class DateAxisDdTest {

	@Autowired
	private EntityManager em;

	private JPAQueryFactory queryFactory;

	@BeforeEach
	public void setup(){
		queryFactory = new JPAQueryFactory(em);

//		DateAxisDd dd = new DateAxisDd();
	}

	@Test
	public void test1(){
		System.out.println("test1");
	}
}

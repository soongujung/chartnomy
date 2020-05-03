package com.chartnomy.indicators.h2.rawquery;

import com.chartnomy.indicators.api.web.trending.index.dto.QTrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.domain.axis.entity.DateAxisDd;
import com.chartnomy.indicators.domain.axis.entity.QDateAxisDd;
import com.chartnomy.indicators.domain.exchange.entity.QExchangeRateWonDollar;
import com.chartnomy.indicators.domain.kospi.entity.Kospi;
import com.chartnomy.indicators.domain.kospi.entity.QKospi;
import com.chartnomy.indicators.domain.loan.entity.QLoanKr;
import com.chartnomy.indicators.domain.loan.entity.QLoanUs;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
//@Transactional
//@Commit
public class SelectTrendingTest {

	@Autowired
	private EntityManager em;

	private JPAQueryFactory queryFactory;

	@BeforeEach
	public void setup(){
		queryFactory = new JPAQueryFactory(em);
	}

	@Test
	public void test1(){
		System.out.println("test1");
		QDateAxisDd dateAxisDd = QDateAxisDd.dateAxisDd;
		List<DateAxisDd> data = queryFactory.selectFrom(dateAxisDd)
			.fetch();
		System.out.println(data);
	}

	@Test
	public void leftJoinTest1(){
		QDateAxisDd dateAxisDd = QDateAxisDd.dateAxisDd;
		QKospi kospi = QKospi.kospi;
		QLoanKr loanKr = QLoanKr.loanKr;
		QLoanUs loanUs = QLoanUs.loanUs;
		QExchangeRateWonDollar exchangeRateWonDollar = QExchangeRateWonDollar.exchangeRateWonDollar;

		List<TrendingDto> result =
			queryFactory.select(
				new QTrendingDto(
					kospi.itemCode1.as("itemCode1"),
					dateAxisDd.date.as("date"),
					kospi.price.as("kospiPrice"),
					loanUs.price.as("loanUsPrice"),
					loanKr.price.as("loanKrPrice"),
					exchangeRateWonDollar.price.as("exchangeRateWonDollar")
				)
			)
			.from(dateAxisDd)
			.leftJoin(kospi)
				.on(dateAxisDd.date.eq(kospi.time))
			.leftJoin(loanKr)
				.on(dateAxisDd.date.eq(loanKr.time))
			.leftJoin(loanUs)
				.on(dateAxisDd.date.eq(loanUs.time))
			.leftJoin(exchangeRateWonDollar)
				.on(dateAxisDd.date.eq(exchangeRateWonDollar.time))
			.where(dateAxisDd.date.loe(LocalDateTime.now()))
			.fetch();

//		for(TrendingDto d : result){
//			System.out.println(d.getDate() +
//				"\n kospiPrice  :: " + d.getKospiPrice() +
//				"\n loanKrPrice :: " + d.getLoanKrPrice() +
//				"\n laonUsPrice :: " + d.getLoanUsPrice() +
//				"\n exchangeRateWonDollar :: " + d.getExchangeWonDallor()
//				);
//		}
	}

	@Test
	public void leftJoinTest2(){
		QDateAxisDd dateAxisDd = QDateAxisDd.dateAxisDd;
		QKospi kospi = QKospi.kospi;

		List<Kospi> result = queryFactory.selectFrom(kospi)
			.leftJoin(dateAxisDd)
//			.on(dateAxisDd.date.eq(kospi.time))
			.on(kospi.time.eq(dateAxisDd.date))
			.fetch();

		for(Kospi k : result){
			System.out.println(k.getTime());
		}
	}

	@Test
	public void joinTest(){
		QDateAxisDd dateAxisDd = QDateAxisDd.dateAxisDd;
		QKospi kospi = QKospi.kospi;

		List<DateAxisDd> data = queryFactory.selectFrom(dateAxisDd)
			.join(kospi)
			.on(dateAxisDd.date.eq(kospi.time))
			.fetch();

		for(DateAxisDd d : data){
			System.out.println(d.getDate());
		}
	}
}

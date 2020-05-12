package com.chartnomy.indicators.testprod.api.web.index.collectors;

import static com.chartnomy.indicators.domain.axis.entity.QDateAxisDd.dateAxisDd;
import static com.chartnomy.indicators.domain.kospi.entity.QKospi.kospi;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

import com.chartnomy.indicators.domain.axis.entity.QDateAxisDd;
import com.chartnomy.indicators.domain.kospi.entity.Kospi;
import com.chartnomy.indicators.domain.kospi.entity.QKospi;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("testprod")
public class MinMaxPriceTest {

	@Autowired
	private EntityManager em;

	private JPAQueryFactory queryFactory;

	@BeforeEach
	public void setup(){
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Test
	public void testCollectMinKospi(){
		List<Kospi> kospiList = queryFactory.selectFrom(kospi)
			.leftJoin(dateAxisDd)
			.on(dateAxisDd.date.eq(kospi.time))
			.fetch();

		Comparator<Kospi> kospiComp = Comparator.comparingDouble(Kospi::getPrice);

		Map<LocalDateTime, Optional<Kospi>> collect = kospiList.stream()
			.map(k -> {
				if (k.getPrice() == null) {
					k.setPrice(0.00);
				}
				return k;
			})
			.collect(groupingBy(Kospi::getTime, maxBy(comparingDouble(Kospi::getPrice))));

		System.out.println(kospiComp);
	}
}

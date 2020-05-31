package com.chartnomy.indicators.testprod.api.web.index.collectors;

import static com.chartnomy.indicators.domain.axis.entity.QDateAxisDd.dateAxisDd;
import static com.chartnomy.indicators.domain.kospi.entity.QKospi.kospi;
import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;

import com.chartnomy.indicators.domain.axis.entity.DateAxisDd;
import com.chartnomy.indicators.domain.kospi.entity.Kospi;
import com.chartnomy.indicators.extensions.TimerTestExtension;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(TimerTestExtension.class)
@SpringBootTest
@ActiveProfiles("testprod")
public class MinMaxPriceTest{

	@Autowired
	private EntityManager em;

	private JPAQueryFactory queryFactory;

	@BeforeEach
	public void setup(){
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Test
	public void testDateAxisDd(){
		List<DateAxisDd> fetch = queryFactory.selectFrom(dateAxisDd)
			.fetch();
	}

	@DisplayName("코스피 월별 최대값 산출")
	@Test
	public void testCollectMaxKospi(){
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

	@DisplayName("코스피 월별 평균값 산출 ")
	@Test
	public void testCollectAvgKospi(){
		List<Kospi> kospiList = queryFactory.selectFrom(kospi)
			.leftJoin(dateAxisDd)
			.on(dateAxisDd.date.eq(kospi.time))
			.fetch();

		Map<String, Double> collectedData = kospiList.stream()
			.map(k -> {
				if (k.getPrice() == null) {
					k.setPrice(0.00);
				}
				return k;
			})
			.collect(groupingBy(Kospi::getYYYYMM, averagingDouble(Kospi::getPrice)));

		// 데이터 Sorting
		Map<String, Double> result = new LinkedHashMap<>();

		collectedData.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.forEachOrdered(x->result.put(x.getKey(), x.getValue()));
	}

	@DisplayName("KOSPI 의 월별 최대/최소/평균 값 산출")
	@Test
	public void testCollectAllKospi(){
		List<Kospi> kospiList = queryFactory.selectFrom(kospi)
			.leftJoin(dateAxisDd)
			.on(dateAxisDd.date.eq(kospi.time))
			.fetch();

		Comparator<Kospi> kospiComp = Comparator.comparingDouble(Kospi::getPrice);

		Map<String, Optional<Kospi>> max = kospiList.parallelStream()
			.map(k -> {
				if (k.getPrice() == null) {
					k.setPrice(0.00);
				}
				return k;
			})
			.collect(groupingBy(Kospi::getYYYYMM, maxBy(kospiComp)));

		Map<String, Optional<Kospi>> min = kospiList.parallelStream()
			.map(k -> {
				if (k.getPrice() == null) {
					k.setPrice(0.00);
				}
				return k;
			})
			.collect(groupingBy(Kospi::getYYYYMM, minBy(kospiComp)));

		Map<String, Double> avg = kospiList.parallelStream()
			.map(k -> {
				if (k.getPrice() == null) {
					k.setPrice(0.00);
				}
				return k;
			})
			.collect(groupingBy(Kospi::getYYYYMM, averagingDouble(Kospi::getPrice)));
	}
}

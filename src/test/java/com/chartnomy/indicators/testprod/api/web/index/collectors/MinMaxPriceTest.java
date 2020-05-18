package com.chartnomy.indicators.testprod.api.web.index.collectors;

import static com.chartnomy.indicators.domain.axis.entity.QDateAxisDd.dateAxisDd;
import static com.chartnomy.indicators.domain.kospi.entity.QKospi.kospi;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;

import com.chartnomy.indicators.domain.axis.entity.QDateAxisDd;
import com.chartnomy.indicators.domain.kospi.entity.Kospi;
import com.chartnomy.indicators.domain.kospi.entity.QKospi;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
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

		result.entrySet().stream()
			.forEach(entry->{
				System.out.println(entry.getKey() + " :: " + entry.getValue().getPrice());
			});
	}

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

		collectedData.entrySet().stream()
			.forEach(entry->{
				System.out.println(entry.getKey() + " :: " + entry.getValue());
			});
	}

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

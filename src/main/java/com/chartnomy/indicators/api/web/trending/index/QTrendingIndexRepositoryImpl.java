package com.chartnomy.indicators.api.web.trending.index;

import static com.chartnomy.indicators.domain.axis.entity.QDateAxisDd.dateAxisDd;
import static com.chartnomy.indicators.domain.exchange.entity.QExchangeRateWonDollar.exchangeRateWonDollar;
import static com.chartnomy.indicators.domain.kospi.entity.QKospi.kospi;
import static com.chartnomy.indicators.domain.loan.entity.QLoanKr.loanKr;
import static com.chartnomy.indicators.domain.loan.entity.QLoanUs.loanUs;

import com.chartnomy.indicators.api.common.IndicatorType;
import com.chartnomy.indicators.api.common.PeriodType;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexDateDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexValueDto;
import com.chartnomy.indicators.api.web.trending.index.dto.QIndexDateDto;
import com.chartnomy.indicators.api.web.trending.index.dto.QIndexValueDto;
import com.chartnomy.indicators.api.web.trending.index.dto.QTrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingMonthCollectDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingParameter;
import com.chartnomy.indicators.domain.axis.entity.QDateAxisDd;
import com.chartnomy.indicators.domain.exchange.entity.QExchangeRateWonDollar;
import com.chartnomy.indicators.domain.kospi.entity.QKospi;
import com.chartnomy.indicators.domain.loan.entity.QLoanKr;
import com.chartnomy.indicators.domain.loan.entity.QLoanUs;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

/**
 * 코스피/나스닥, 환율, 금리(미국/한국) 차트를 위한 일별 데이터 조회 Repository
 */
@Repository
public class QTrendingIndexRepositoryImpl implements QTrendingIndexRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public QTrendingIndexRepositoryImpl(EntityManager em){
			this.em = em;
			this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<TrendingDto> getDefaultTrendingResult() {
		QDateAxisDd dateAxisDd = QDateAxisDd.dateAxisDd;
		QKospi kospi = QKospi.kospi;
		QLoanKr loanKr = QLoanKr.loanKr;
		QLoanUs loanUs = QLoanUs.loanUs;
		QExchangeRateWonDollar exchangeRateWonDollar = QExchangeRateWonDollar.exchangeRateWonDollar;

		List<TrendingDto> result = queryFactory.select(
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
//		.where(dateAxisDd.date.loe(LocalDateTime.now()))
		.fetch();
		return result;
	}

	public List<TrendingMonthCollectDto> getTrendingMonthCollectResult(IndicatorType indicatorType, TrendingParameter parameter) {
		return indicatorType.getTrendingMonthResult(queryFactory, parameter);
	}

	@Override
	public List<IndexValueDto> getKospiResult(LocalDateTime fromDate, LocalDateTime toDate,
												PeriodType periodType) {

			List<IndexValueDto> result = queryFactory.select(
				new QIndexValueDto(
					dateAxisDd.date.as("date"),
					kospi.price.as("value")
				)
			)
			.from(dateAxisDd)
			.leftJoin(kospi)
			.on(dateAxisDd.date.eq(kospi.time).and(dateAxisDd.date.between(fromDate, toDate)))
				.where(dateAxisDd.date.between(fromDate, toDate))
			.fetch();

		return result;
	}

	@Override
	public List<IndexValueDto> getExchangeRateDollar(LocalDateTime fromDate, LocalDateTime toDate,
													 PeriodType periodType) {

		List<IndexValueDto> result = queryFactory.select(
			new QIndexValueDto(
				dateAxisDd.date.as("date"),
				exchangeRateWonDollar.price.as("value")
			)
		)
		.from(dateAxisDd)
		.leftJoin(exchangeRateWonDollar)
		.on(dateAxisDd.date.eq(exchangeRateWonDollar.time).and(dateAxisDd.date.between(fromDate, toDate)))
			.where(dateAxisDd.date.between(fromDate, toDate))
		.fetch();

		return result;
	}

	@Override
	public List<IndexValueDto> getLoanKrRate(LocalDateTime fromDate, LocalDateTime toDate,
											 PeriodType periodType) {

		List<IndexValueDto> result = queryFactory.select(
			new QIndexValueDto(
				dateAxisDd.date.as("date"),
				loanKr.price.as("value")
			)
		)
		.from(dateAxisDd)
		.leftJoin(loanKr)
		.on(dateAxisDd.date.eq(loanKr.time).and(dateAxisDd.date.between(fromDate, toDate)))
			.where(dateAxisDd.date.between(fromDate, toDate))
		.fetch();

		return result;
	}

	@Override
	public List<IndexValueDto> getLoanUsRate(LocalDateTime fromDate, LocalDateTime toDate,
												PeriodType periodType) {

		List<IndexValueDto> result = queryFactory.select(
			new QIndexValueDto(
				dateAxisDd.date.as("date"),
				loanUs.price.as("value")
			)
		)
		.from(dateAxisDd)
		.leftJoin(loanUs)
		.on(dateAxisDd.date.eq(loanUs.time).and(dateAxisDd.date.between(fromDate, toDate)))
			.where(dateAxisDd.date.between(fromDate, toDate))
		.fetch();

		return result;
	}

	@Override
	public List<IndexDateDto> getDateSeries( LocalDateTime fromDate, LocalDateTime toDate,
											 PeriodType periodType) {

		List<IndexDateDto> dateList = queryFactory.select(
			new QIndexDateDto(
				dateAxisDd.date.as("date")
			)
		)
		.from(dateAxisDd)
		.where(
			dateAxisDd.date.goe(fromDate).and(dateAxisDd.date.loe(toDate))
		)
		.fetch();

		return dateList;
	}
}

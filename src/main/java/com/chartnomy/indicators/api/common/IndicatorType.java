package com.chartnomy.indicators.api.common;

import com.chartnomy.indicators.api.web.trending.index.dto.QTrendingMonthCollectDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingMonthCollectDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingParameter;
import com.chartnomy.indicators.domain.axis.entity.QDateAxisDd;
import com.chartnomy.indicators.domain.exchange.entity.QExchangeRateWonDollar;
import com.chartnomy.indicators.domain.kospi.entity.QKospi;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;

public enum IndicatorType {
	KOSPI("KOSPI", 1){
		@Override
		public List<TrendingMonthCollectDto> getTrendingMonthResult(JPAQueryFactory queryFactory, TrendingParameter parameter) {
			QKospi kospi = QKospi.kospi;
			QDateAxisDd dateAxisDd = QDateAxisDd.dateAxisDd;
			List<TrendingMonthCollectDto> result = queryFactory.select(
					new QTrendingMonthCollectDto(
						kospi.price.min(),
						kospi.price.max(),
						kospi.price.avg()
					)
				)
				.from(dateAxisDd)
				.leftJoin(kospi)
				.on(dateAxisDd.date.eq(kospi.time))
				.groupBy(dateAxisDd.date)
				.fetch();
			return result;
		}

		@Override
		public void setDataType() {
			setDataType(DataType.DAILY);
		}
	},
	EXCHANGE_RATE_DOLLAR("EXCHANGE_RATE_DOLLAR", 2){
		@Override
		public List<TrendingMonthCollectDto> getTrendingMonthResult(JPAQueryFactory queryFactory, TrendingParameter parameter) {
			QExchangeRateWonDollar exchangeRateWonDollar = QExchangeRateWonDollar.exchangeRateWonDollar;
			QDateAxisDd dateAxisDd = QDateAxisDd.dateAxisDd;
			List<TrendingMonthCollectDto> result = queryFactory.select(
				new QTrendingMonthCollectDto(
					exchangeRateWonDollar.price.min(),
					exchangeRateWonDollar.price.max(),
					exchangeRateWonDollar.price.avg()
				)
			)
				.from(dateAxisDd)
				.leftJoin(exchangeRateWonDollar)
				.on(dateAxisDd.date.eq(exchangeRateWonDollar.time))
				.groupBy(dateAxisDd.date.dayOfMonth())
				.fetch();
			return result;
		}

		@Override
		public void setDataType() {
			setDataType(DataType.DAILY);
		}
	},
	LOAN_RATE_KR("LOAN_RATE_KR", 3){
		// 월별
		@Override
		public List<TrendingMonthCollectDto> getTrendingMonthResult(JPAQueryFactory queryFactory, TrendingParameter parameter) {
			return null;
		}

		@Override
		public void setDataType() {
			setDataType(DataType.MONTHLY);
		}
	},
	LOAN_RATE_US("LOAN_RATE_US", 4){
		// 월별
		@Override
		public List<TrendingMonthCollectDto> getTrendingMonthResult(JPAQueryFactory queryFactory, TrendingParameter parameter) {
			return null;
		}

		@Override
		public void setDataType() {
			setDataType(DataType.MONTHLY);
		}
	};
	// S&P500
//	SP500("SP500", 5){
//		// 일별
//		@Override
//		public List<TrendingMonthCollectDto> getTrendingMonthResult(JPAQueryFactory queryFactory, TrendingParameter parameter) {
//			return null;
//		}
//
//		@Override
//		public void setDataType() {
//			setDataType(DataType.DAILY);
//		}
//	};

	private String indicatorTypeNm;
	private int indicatorTypeCd;
	private QDateAxisDd qDateAxisDd = QDateAxisDd.dateAxisDd;
	private DataType dataType;

	IndicatorType(String indicatorTypeNm, int indicatorTypeCd){
		this.indicatorTypeNm = indicatorTypeNm;
		this.indicatorTypeCd = indicatorTypeCd;
		this.setDataType();
	}

	public <T> T getType(T type){
		return type;
	}

	public abstract List<TrendingMonthCollectDto> getTrendingMonthResult(JPAQueryFactory queryFactory, TrendingParameter parameter);

	public abstract void setDataType();

	public String getIndicatorTypeNm() {
		return indicatorTypeNm;
	}

	public void setIndicatorTypeNm(String indicatorTypeNm) {
		this.indicatorTypeNm = indicatorTypeNm;
	}

	public int getIndicatorTypeCd() {
		return indicatorTypeCd;
	}

	public void setIndicatorTypeCd(int indicatorTypeCd) {
		this.indicatorTypeCd = indicatorTypeCd;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
}

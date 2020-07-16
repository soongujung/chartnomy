package com.chartnomy.indicators.domain.exchange.types;

import com.chartnomy.indicators.api.common.PeriodType;
import com.chartnomy.indicators.api.web.trending.index.QTrendingIndexRepository;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexValueDto;
import java.time.LocalDateTime;
import java.util.List;

public enum ExchangeCurrencyType {
	USD("USD", 1){
		@Override
		public List<IndexValueDto> getExchangeRate( QTrendingIndexRepository repository,
													LocalDateTime fromDate, LocalDateTime toDate,
													PeriodType periodType) {
			return repository.getExchangeRateDollar(fromDate, toDate, periodType);
		}
	};

	ExchangeCurrencyType(String currencyTypeNm, int currencyTypeCd){
		this.currencyTypeNm = currencyTypeNm;
		this.currencyTypeCd = currencyTypeCd;
	}

	private String currencyTypeNm;
	private int currencyTypeCd;

	public abstract List<IndexValueDto> getExchangeRate( QTrendingIndexRepository repository,
														 LocalDateTime fromDate, LocalDateTime toDate,
														 PeriodType periodType);

	public String getCurrencyTypeNm() {
		return currencyTypeNm;
	}

	public void setCurrencyTypeNm(String currencyTypeNm) {
		this.currencyTypeNm = currencyTypeNm;
	}

	public int getCurrencyTypeCd() {
		return currencyTypeCd;
	}

	public void setCurrencyTypeCd(int currencyTypeCd) {
		this.currencyTypeCd = currencyTypeCd;
	}
}

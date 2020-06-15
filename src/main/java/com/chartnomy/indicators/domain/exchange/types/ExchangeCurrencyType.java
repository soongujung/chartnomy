package com.chartnomy.indicators.domain.exchange.types;

import com.chartnomy.indicators.api.web.trending.index.QTrendingIndexRepository;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexPriceDto;
import java.time.LocalDateTime;
import java.util.List;

public enum ExchangeCurrencyType {
	USD("USD", 1){
		@Override
		public List<IndexPriceDto> getExchangeRate(QTrendingIndexRepository repository, LocalDateTime fromDate, LocalDateTime toDate) {
			return repository.getExchangeRateDollar(fromDate, toDate);
		}
	};

	ExchangeCurrencyType(String currencyTypeNm, int currencyTypeCd){
		this.currencyTypeNm = currencyTypeNm;
		this.currencyTypeCd = currencyTypeCd;
	}

	private String currencyTypeNm;
	private int currencyTypeCd;

	public abstract List<IndexPriceDto> getExchangeRate(QTrendingIndexRepository repository, LocalDateTime fromDate, LocalDateTime toDate);

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

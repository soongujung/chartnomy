package com.chartnomy.indicators.domain.exchange.types;

import com.chartnomy.indicators.api.web.trending.index.QTrendingIndexRepository;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingIndexDto;
import java.util.List;

public enum CurrencyType {
	USD("USD", 1){
		@Override
		public List<TrendingIndexDto> getExchangeRate(QTrendingIndexRepository repository) {
			return repository.getExchangeRateDollar();
		}
	};

	CurrencyType(String currencyTypeNm, int currencyTypeCd){
		this.currencyTypeNm = currencyTypeNm;
		this.currencyTypeCd = currencyTypeCd;
	}

	private String currencyTypeNm;
	private int currencyTypeCd;

	public abstract List<TrendingIndexDto> getExchangeRate(QTrendingIndexRepository repository);

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

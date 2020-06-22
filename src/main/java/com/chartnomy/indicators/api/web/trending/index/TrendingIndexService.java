package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.common.IndicatorType;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexDateDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexPriceDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexRateDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingMonthCollectDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingParameter;
import com.chartnomy.indicators.domain.exchange.types.ExchangeCurrencyType;
import com.chartnomy.indicators.domain.loan.types.LoanType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TrendingIndexService {

	List<TrendingDto> getDefaultTrendingResult();

	Map<String, List<TrendingMonthCollectDto>> getTrendingMonthCollectResult(TrendingParameter parameter);

	List<TrendingMonthCollectDto> getTrendingMonthCollectResult(IndicatorType indicatorType, TrendingParameter parameter);

	List<IndexPriceDto> getKospiResult(LocalDateTime fromDate, LocalDateTime toDate);

	List<IndexPriceDto> getExchangeRate(ExchangeCurrencyType exchangeCurrencyType, LocalDateTime fromDate, LocalDateTime toDate);

	List<IndexRateDto> getLoanRate(LoanType loanType, LocalDateTime fromDate, LocalDateTime toDate);

	List<IndexDateDto> getDateSeries(LocalDateTime fromDate, LocalDateTime toDate);
}

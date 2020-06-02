package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.common.IndicatorType;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingIndexDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingMonthCollectDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingParameter;
import com.chartnomy.indicators.domain.exchange.types.CurrencyType;
import java.util.List;
import java.util.Map;

public interface TrendingIndexService {

	List<TrendingDto> getDefaultTrendingResult();

	Map<String, List<TrendingMonthCollectDto>> getTrendingMonthCollectResult(TrendingParameter parameter);

	List<TrendingMonthCollectDto> getTrendingMonthCollectResult(IndicatorType indicatorType, TrendingParameter parameter);

	List<TrendingIndexDto> getKospiResult();

	List<TrendingIndexDto> getExchangeRate(CurrencyType currencyType);
}

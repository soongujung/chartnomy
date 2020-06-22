package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.common.IndicatorType;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexDateDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexPriceDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexRateDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingMonthCollectDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingParameter;
import java.time.LocalDateTime;
import java.util.List;

public interface QTrendingIndexRepository {

	List<TrendingDto> getDefaultTrendingResult();

	List<TrendingMonthCollectDto> getTrendingMonthCollectResult(IndicatorType indicatorType, TrendingParameter parameter);

	List<IndexPriceDto> getKospiResult(LocalDateTime fromDate, LocalDateTime toDate);

	List<IndexPriceDto> getExchangeRateDollar(LocalDateTime fromDate, LocalDateTime toDate);

	List<IndexRateDto> getLoanKrRate(LocalDateTime fromDate, LocalDateTime toDate);

	List<IndexRateDto> getLoanUsRate(LocalDateTime fromDate, LocalDateTime toDate);

	List<IndexDateDto> getDateResult(LocalDateTime fromDate, LocalDateTime toDate);
}

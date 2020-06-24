package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.common.IndicatorType;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexDateDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexValueDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingMonthCollectDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingParameter;
import java.time.LocalDateTime;
import java.util.List;

public interface QTrendingIndexRepository {

	List<TrendingDto> getDefaultTrendingResult();

	List<TrendingMonthCollectDto> getTrendingMonthCollectResult(IndicatorType indicatorType, TrendingParameter parameter);

	List<IndexValueDto> getKospiResult(LocalDateTime fromDate, LocalDateTime toDate);

	List<IndexValueDto> getExchangeRateDollar(LocalDateTime fromDate, LocalDateTime toDate);

	List<IndexValueDto> getLoanKrRate(LocalDateTime fromDate, LocalDateTime toDate);

	List<IndexValueDto> getLoanUsRate(LocalDateTime fromDate, LocalDateTime toDate);

	List<IndexDateDto> getDateSeries(LocalDateTime fromDate, LocalDateTime toDate);
}

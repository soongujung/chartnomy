package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.common.IndicatorType;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingIndexDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingMonthCollectDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingParameter;
import java.util.List;

public interface QTrendingIndexRepository {

	List<TrendingDto> getDefaultTrendingResult();

	List<TrendingMonthCollectDto> getTrendingMonthCollectResult(IndicatorType indicatorType, TrendingParameter parameter);

	List<TrendingIndexDto> getKospiResult();
}

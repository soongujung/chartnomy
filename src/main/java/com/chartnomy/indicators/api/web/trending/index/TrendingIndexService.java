package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import java.util.List;

public interface TrendingIndexService {

	List<TrendingDto> getDefaultTrendingResult();
}

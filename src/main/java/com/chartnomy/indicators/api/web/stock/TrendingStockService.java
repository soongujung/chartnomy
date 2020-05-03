package com.chartnomy.indicators.api.web.stock;

import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import java.util.List;

public interface TrendingStockService {

	List<TrendingDto> getDefaultTrendingResult();
}

package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.domain.kospi.entity.Kospi;
import java.util.List;

public interface QTrendingIndexRepository {

	List<TrendingDto> getDefaultTrendingResult();
}

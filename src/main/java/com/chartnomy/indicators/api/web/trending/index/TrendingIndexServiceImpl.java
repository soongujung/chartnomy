package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.common.DataType;
import com.chartnomy.indicators.api.common.IndicatorType;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingIndexDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingMonthCollectDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingParameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrendingIndexServiceImpl implements TrendingIndexService{

	private final QTrendingIndexRepository qIndexRepository;

	@Override
	public List<TrendingDto> getDefaultTrendingResult() {
		return qIndexRepository.getDefaultTrendingResult();
	}

	/**
	 * All Indicator
	 * @param parameter
	 * @return
	 */
	@Override
	public Map<String, List<TrendingMonthCollectDto>> getTrendingMonthCollectResult(TrendingParameter parameter) {
		Map<String, List<TrendingMonthCollectDto>> data = new HashMap<>();
		Arrays.stream(IndicatorType.values()).forEach(indicatorType ->{
			boolean isDaily = indicatorType.getDataType().equals(DataType.DAILY);
			if(isDaily){
				List<TrendingMonthCollectDto> monthResult = qIndexRepository.getTrendingMonthCollectResult(indicatorType, parameter);
				data.put(indicatorType.getIndicatorTypeNm(), monthResult);
			}
		});
		return data;
	}

	/**
	 * Each Indicator
	 * @param indicatorType
	 * @param parameter
	 * @return
	 */
	@Override
	public List<TrendingMonthCollectDto> getTrendingMonthCollectResult(IndicatorType indicatorType, TrendingParameter parameter) {

		return null;
	}

	@Override
	public List<TrendingIndexDto> getKospiResult() {
		return qIndexRepository.getKospiResult();
	}
}

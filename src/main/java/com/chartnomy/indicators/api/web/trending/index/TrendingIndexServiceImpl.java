package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.common.DataType;
import com.chartnomy.indicators.api.common.IndicatorType;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexPriceDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexRateDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingMonthCollectDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingParameter;
import com.chartnomy.indicators.domain.exchange.types.ExchangeCurrencyType;
import com.chartnomy.indicators.domain.loan.types.LoanType;
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
	public List<IndexPriceDto> getKospiResult() {
		return qIndexRepository.getKospiResult();
	}

	@Override
	public List<IndexPriceDto> getExchangeRate(ExchangeCurrencyType exchangeCurrencyType) {
		return exchangeCurrencyType.getExchangeRate(qIndexRepository);
	}

	@Override
	public List<IndexRateDto> getLoanRate(LoanType loanType) {
		return loanType.getLoanRate(qIndexRepository);
	}

}

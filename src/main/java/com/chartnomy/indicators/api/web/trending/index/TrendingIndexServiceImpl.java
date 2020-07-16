package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.common.DataType;
import com.chartnomy.indicators.api.common.IndicatorType;
import com.chartnomy.indicators.api.common.PeriodType;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexDateDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexValueDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingMonthCollectDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingParameter;
import com.chartnomy.indicators.domain.exchange.types.ExchangeCurrencyType;
import com.chartnomy.indicators.domain.loan.types.LoanType;
import java.time.LocalDateTime;
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
	public List<IndexValueDto> getKospiResult(LocalDateTime fromDate, LocalDateTime toDate,
												PeriodType periodType) {
		return qIndexRepository.getKospiResult(fromDate, toDate, periodType);
	}

	@Override
	public List<IndexValueDto> getExchangeRate( ExchangeCurrencyType exchangeCurrencyType,
												LocalDateTime fromDate, LocalDateTime toDate,
												PeriodType periodType) {
		return exchangeCurrencyType.getExchangeRate(qIndexRepository, fromDate, toDate, periodType);
	}

	@Override
	public List<IndexValueDto> getLoanRate( LoanType loanType,
											LocalDateTime fromDate, LocalDateTime toDate,
											PeriodType periodType) {
		return loanType.getLoanRate(qIndexRepository, fromDate, toDate, periodType);
	}

	@Override
	public List<IndexDateDto> getDateSeries( LocalDateTime fromDate, LocalDateTime toDate,
											 PeriodType periodType) {
		return qIndexRepository.getDateSeries(fromDate, toDate, periodType);
	}

}

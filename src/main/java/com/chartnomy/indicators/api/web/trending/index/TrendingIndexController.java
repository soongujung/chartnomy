package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.common.PeriodType;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexDateDto;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexValueDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.domain.exchange.types.ExchangeCurrencyType;
import com.chartnomy.indicators.domain.loan.types.LoanType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * KOSPI, 주가지표, 금리(미국/한국), 환율 차트 컨트롤러
 */
@Controller
@RequiredArgsConstructor
public class TrendingIndexController {

	private final TrendingIndexService trendingIndexService;

	@GetMapping(value = {"/", "/trending"})
	public String getIndexPage(){
		return "/trending/index";
	}

	@GetMapping("/api/web/stock/trending")
	public @ResponseBody List<TrendingDto> getDefaultTrendingResult(){
		return trendingIndexService.getDefaultTrendingResult();
	}

	@GetMapping("/api/web/trending/index/DATE")
	public @ResponseBody List<IndexDateDto> getDateSeries(
							@RequestParam("from") String from,
							@RequestParam("to") String to,
							@RequestParam(value = "periodType", required = false) String periodType
	){
		LocalDateTime fromDate = processDateParam(from);
		LocalDateTime toDate = processDateParam(to);
		return trendingIndexService.getDateSeries(fromDate, toDate);
	}

	@GetMapping("/api/web/trending/index/KOSPI")
	public @ResponseBody List<IndexValueDto> getKospiResult(
							@RequestParam("from") String from,
							@RequestParam("to") String to,
							@RequestParam(value = "periodType", required = false) String periodType
		){
		LocalDateTime fromDate = processDateParam(from);
		LocalDateTime toDate = processDateParam(to);
		return trendingIndexService.getKospiResult(fromDate, toDate);
	}

	@GetMapping("/api/web/trending/index/exchange/{currencyTypeNm}")
	public @ResponseBody List<IndexValueDto> getExchangeRate(
							@PathVariable("currencyTypeNm") String currencyTypeNm,
							@RequestParam("from") String from,
							@RequestParam("to") String to,
							@RequestParam(value = "periodType", required = false) String periodType
		){
		ExchangeCurrencyType exchangeCurrencyType = ExchangeCurrencyType.valueOf(currencyTypeNm);
		LocalDateTime fromDate = processDateParam(from);
		LocalDateTime toDate = processDateParam(to);
		return trendingIndexService.getExchangeRate(exchangeCurrencyType, fromDate, toDate);
	}

	@GetMapping("/api/web/trending/index/loan/{loanType}")
	public @ResponseBody List<IndexValueDto> getLoanRate(
							@PathVariable("loanType") String loanTypeNm,
							@RequestParam("from") String from,
							@RequestParam("to") String to,
							@RequestParam(value = "periodType", required = false) String periodType
		){
		LoanType loanType = LoanType.valueOf(loanTypeNm);
		LocalDateTime fromDate = processDateParam(from);
		LocalDateTime toDate = processDateParam(to);
		return trendingIndexService.getLoanRate(loanType, fromDate, toDate);
	}

	private LocalDateTime processDateParam(String strDate){
		return LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	}

	private PeriodType processPeriodType(String periodType){
		if(periodType != null){
			return PeriodType.valueOf(periodType);
		}
		else{
			return PeriodType.DATE;
		}
	}
}

package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingIndexDto;
import com.chartnomy.indicators.domain.exchange.types.CurrencyType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/api/web/trending/index/kospi")
	public @ResponseBody List<TrendingIndexDto> getKospiResult(){
		return trendingIndexService.getKospiResult();
	}

	@GetMapping("/api/web/trending/index/exchange/{currencyTypeNm}")
	public @ResponseBody List<TrendingIndexDto> getExchangeRate(@PathVariable("currencyTypeNm") String currencyTypeNm){
		CurrencyType currencyType = CurrencyType.valueOf(currencyTypeNm);
		return trendingIndexService.getExchangeRate(currencyType);
	}
}

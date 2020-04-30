package com.chartnomy.indicators.api.web.trending.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * KOSPI, 주가지표, 금리(미국/한국), 환율 차트 컨트롤러
 */
@Controller
public class TrendingIndexController {

	@GetMapping(value = {"/", "/trending"})
	public String getIndexPage(){
		return "/trending/index";
	}
}

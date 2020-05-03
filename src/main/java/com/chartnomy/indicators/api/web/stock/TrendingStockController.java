package com.chartnomy.indicators.api.web.stock;

import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 기업별 주요 재무제표 (PER, PBR, EPS, ROE...., 부채비율 등) 차트 컨트롤러
 */
@Controller
@RequiredArgsConstructor
public class TrendingStockController {

	private final TrendingStockService trendingStockService;
}

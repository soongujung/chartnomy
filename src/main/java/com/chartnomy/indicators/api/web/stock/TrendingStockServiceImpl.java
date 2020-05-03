package com.chartnomy.indicators.api.web.stock;

import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrendingStockServiceImpl implements TrendingStockService{

	private final QTrendingStockRepository qRepository;

	@Override
	public List<TrendingDto> getDefaultTrendingResult() {
		return qRepository.getDefaultTrendingResult();
	}
}

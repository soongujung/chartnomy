package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import java.util.List;
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
}

package com.chartnomy.indicators.api.web.trending.index.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TrendingParameter {
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}

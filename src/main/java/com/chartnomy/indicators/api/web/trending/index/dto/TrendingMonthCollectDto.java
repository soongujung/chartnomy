package com.chartnomy.indicators.api.web.trending.index.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class TrendingMonthCollectDto {

	private Double	minValue;
	private Double	maxValue;
	private Double	avgValue;
	private String	month;

	@QueryProjection
	public TrendingMonthCollectDto(
		Double minValue, Double maxValue, Double avgValue, String month
	){
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.avgValue = avgValue;
		this.month = month;
	}
}

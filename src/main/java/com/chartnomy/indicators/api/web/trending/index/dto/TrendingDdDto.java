package com.chartnomy.indicators.api.web.trending.index.dto;

import lombok.Data;

/**
 * 일별 데이터의 종합적인 추이를 월별 최대/최소/평균 산출
 */
@Data
public class TrendingDdDto {
	private String indexNm;
	private Double min;
	private Double max;
	private Double avg;
}

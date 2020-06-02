package com.chartnomy.indicators.api.web.trending.index.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class IndexRateDto {

	@JsonFormat(shape = Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
	private LocalDateTime date;
	private Double rate;

	@QueryProjection
	public IndexRateDto(
		LocalDateTime date, Double rate
	){
		this.date = date;
		this.rate = rate;
	}
}

package com.chartnomy.indicators.api.web.trending.index.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TrendingIndexDto {

	@JsonFormat(shape = Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
	private LocalDateTime date;
	private Double	price;

	@QueryProjection
	public TrendingIndexDto(
		LocalDateTime date, Double price
	){
		this.date = date;
		this.price = price;
	}
}

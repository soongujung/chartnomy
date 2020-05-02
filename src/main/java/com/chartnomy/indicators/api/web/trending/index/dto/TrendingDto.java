package com.chartnomy.indicators.api.web.trending.index.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TrendingDto {

	private String itemCode1;
	private LocalDateTime date;
	private Double kospiPrice;

	@QueryProjection
	public TrendingDto(String itemCode1, LocalDateTime date, Double kospiPrice){
		this.itemCode1 = itemCode1;
		this.date = date;
		this.kospiPrice = kospiPrice;
	}
}

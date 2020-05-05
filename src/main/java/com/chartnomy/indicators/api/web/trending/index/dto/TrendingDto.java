package com.chartnomy.indicators.api.web.trending.index.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TrendingDto {

	@JsonFormat(shape = Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
	private LocalDateTime 	date;

	private String 			itemCode1;
	private Double 			kospiPrice;
	private Double 			loanUsPrice;
	private Double 			loanKrPrice;
	private Double			exchangeWonDallor;

	@QueryProjection
	public TrendingDto(
			String itemCode1, LocalDateTime date,
//			Double kospiPrice
			Double kospiPrice, Double loanUsPrice, Double loanKrPrice, Double exchangeWonDallor
		){
		this.itemCode1 = itemCode1;
		this.date = date;
		this.kospiPrice = kospiPrice;
		this.loanUsPrice = loanUsPrice;
		this.loanKrPrice = loanKrPrice;
		this.exchangeWonDallor = exchangeWonDallor;
	}
}

package com.chartnomy.indicators.api.web.trending.index.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class IndexValueDto {

	@JsonFormat(shape = Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
	LocalDateTime date;

	Double value;

	@QueryProjection
	public IndexValueDto(LocalDateTime date, Double value){
		this.date = date;
		this.value = value;
	}
}

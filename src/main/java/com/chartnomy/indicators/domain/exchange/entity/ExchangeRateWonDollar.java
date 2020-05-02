package com.chartnomy.indicators.domain.exchange.entity;

import com.chartnomy.indicators.domain.axis.entity.DateAxisDd;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "exchange_rate_dollar_day")
public class ExchangeRateWonDollar {
	@Id @Column(name = "TIME")
	private LocalDateTime time;

	@OneToMany(mappedBy = "exchangeRateWonDollar")
	private List<DateAxisDd> dates = new ArrayList<>();

	@Column(name = "STAT_NAME")
	private String statName;

	@Column(name = "STAT_CODE")
	private String statCode;

	@Column(name = "DATA_VALUE")
	private Double price;

	@Column(name = "ITEM_CODE1")
	private String itemCode1;

	@Column(name = "ITEM_CODE2")
	private String itemCode2;

	@Column(name = "ITEM_CODE3")
	private String itemCode3;

	@Column(name = "ITEM_NAME1")
	private String itemName1;

	@Column(name = "ITEM_NAME2")
	private String itemName2;

	@Column(name = "ITEM_NAME3")
	private String itemName3;

	public ExchangeRateWonDollar(){}

}

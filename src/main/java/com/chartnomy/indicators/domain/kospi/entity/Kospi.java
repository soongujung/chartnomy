package com.chartnomy.indicators.domain.kospi.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "kospi_day")
public class Kospi {
	@Id
	@Column(name = "TIME")
	private LocalDate time;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "v_date")
//	private DateAxisDd date;

	@Column(name = "STAT_NAME")
	private String statName;

	@Column(name = "STAT_CODE")
	private String statCode;

	@Column(name = "DATA_VALUE")
	private String price;

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
}

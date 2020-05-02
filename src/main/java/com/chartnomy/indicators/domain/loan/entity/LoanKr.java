package com.chartnomy.indicators.domain.loan.entity;

import com.chartnomy.indicators.domain.axis.entity.DateAxisDd;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "loan_rate_kor")
public class LoanKr {
	@Id @Column(name = "TIME")
	private LocalDateTime time;

	@OneToMany(mappedBy = "loanKr")
	private List<DateAxisDd> dateList = new ArrayList<>();

	@Column(name = "STAT_NAME")
	private String statName;

	@Column(name = "STAT_CODE")
	private String statCode;

	@Column(name = "ITEM_CODE1")
	private String stock;

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

	@Column(name = "DATA_VALUE")
	private Double price;

	@Column(name = "UNIT_NAME")
	private String unitName;

	public LoanKr(){}
}

package com.chartnomy.indicators.axis.entity;

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
@Table(name = "DATE_AXIS_DD")
public class DateAxisDd {
	@Id
	@Column(name = "v_date")
//	@OneToMany(mappedBy = "date")
	private String date;

	@Column(name = "v_yyyy")
	private String year;

	@Column(name = "v_mm")
	private String month;

	@Column(name = "v_dd")
	private String dayOfMonth;
}

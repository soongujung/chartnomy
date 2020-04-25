package com.chartnomy.indicators.axis.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "DATE_AXIS")
public class DateAxis {
	@Id
	@Column(name = "v_date")
//	@OneToMany(mappedBy = "date")
	private LocalDate date;

	@Column(name = "v_yyyy")
	private String year;

	@Column(name = "v_mm")
	private String month;

	@Column(name = "v_dd")
	private String dayOfMonth;

	public DateAxis(){}
	public DateAxis(String date, String year, String month, String dayOfMonth){

	}
}

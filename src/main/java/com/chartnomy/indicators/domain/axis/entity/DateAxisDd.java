package com.chartnomy.indicators.domain.axis.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "DATE_AXIS_DD")
public class DateAxisDd {
	@Id
	@Column(name = "yyyymmdd")
//	@OneToMany(mappedBy = "date")
	private LocalDateTime date;

	@Column(name = "yyyy")
	private String year;

	@Column(name = "mm")
	private String month;

	@Column(name = "dd")
	private String dayOfMonth;
}

package com.chartnomy.indicators.api.common;

public enum PeriodType {

	DATE(1, "DD"){

	},
	MONTH(2, "MM"){

	},
	YEAR(3, "YY"){

	};

	private int periodTypeCd;
	private String periodTypeNm;

	PeriodType(int periodTypeCd, String periodTypeNm){
		this.periodTypeCd = periodTypeCd;
		this.periodTypeNm = periodTypeNm;
	}

	public int getPeriodTypeCd() {
		return periodTypeCd;
	}

	public void setPeriodTypeCd(int periodTypeCd) {
		this.periodTypeCd = periodTypeCd;
	}

	public String getPeriodTypeNm() {
		return periodTypeNm;
	}

	public void setPeriodTypeNm(String periodTypeNm) {
		this.periodTypeNm = periodTypeNm;
	}
}

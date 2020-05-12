package com.chartnomy.indicators.api.common;

public enum DataType {
	DAILY("DAILY", 1){

	},
	MONTHLY("MONTHLY", 2){

	},
	YEARLY("YEARLY", 3){

	};

	private String dataTypeNm;
	private int dataTypeCd;

	DataType(String dataTypeNm, int dataTypeCd){
		this.dataTypeNm = dataTypeNm;
		this.dataTypeCd = dataTypeCd;
	}

	public String getDataTypeNm() {
		return dataTypeNm;
	}

	public void setDataTypeNm(String dataTypeNm) {
		this.dataTypeNm = dataTypeNm;
	}

	public int getDataTypeCd() {
		return dataTypeCd;
	}

	public void setDataTypeCd(int dataTypeCd) {
		this.dataTypeCd = dataTypeCd;
	}
}

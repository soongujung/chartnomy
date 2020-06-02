package com.chartnomy.indicators.domain.loan.types;

public enum LoanType {
	LOAN_KR("LOAN_KR", 1){

	},
	LOAN_US("LOAN_US", 2){

	};

	private String loanTypeNm;

	private int loanTypeCd;

	LoanType(String loanTypeNm, int loanTypeCd){}


}

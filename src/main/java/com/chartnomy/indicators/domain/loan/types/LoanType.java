package com.chartnomy.indicators.domain.loan.types;

import com.chartnomy.indicators.api.web.trending.index.QTrendingIndexRepository;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexRateDto;
import java.util.List;

public enum LoanType {
	LOAN_KR("LOAN_KR", 1){
		@Override
		public List<IndexRateDto> getLoanRate(QTrendingIndexRepository repository) {
			return repository.getLoanKrRate();
		}
	},
	LOAN_US("LOAN_US", 2){
		@Override
		public List<IndexRateDto> getLoanRate(QTrendingIndexRepository repository) {
			return repository.getLoanUsRate();
		}
	};

	private String loanTypeNm;

	private int loanTypeCd;

	LoanType(String loanTypeNm, int loanTypeCd){}

	public abstract List<IndexRateDto> getLoanRate(QTrendingIndexRepository repository);
}

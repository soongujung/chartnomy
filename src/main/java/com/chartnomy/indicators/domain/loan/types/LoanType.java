package com.chartnomy.indicators.domain.loan.types;

import com.chartnomy.indicators.api.web.trending.index.QTrendingIndexRepository;
import com.chartnomy.indicators.api.web.trending.index.dto.IndexValueDto;
import java.time.LocalDateTime;
import java.util.List;

public enum LoanType {
	LOAN_KR("LOAN_KR", 1){
		@Override
		public List<IndexValueDto> getLoanRate(QTrendingIndexRepository repository,
												LocalDateTime fromDate, LocalDateTime toDate) {
			return repository.getLoanKrRate(fromDate, toDate);
		}
	},
	LOAN_US("LOAN_US", 2){
		@Override
		public List<IndexValueDto> getLoanRate(QTrendingIndexRepository repository,
												LocalDateTime fromDate, LocalDateTime toDate) {
			return repository.getLoanUsRate(fromDate, toDate);
		}
	};

	private String loanTypeNm;

	private int loanTypeCd;

	LoanType(String loanTypeNm, int loanTypeCd){}

	public abstract List<IndexValueDto> getLoanRate(QTrendingIndexRepository repository,
													LocalDateTime fromDate, LocalDateTime toDate);
}

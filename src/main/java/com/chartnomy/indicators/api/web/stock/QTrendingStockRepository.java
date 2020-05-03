package com.chartnomy.indicators.api.web.stock;

import com.chartnomy.indicators.api.web.trending.index.dto.QTrendingDto;
import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.domain.axis.entity.QDateAxisDd;
import com.chartnomy.indicators.domain.exchange.entity.QExchangeRateWonDollar;
import com.chartnomy.indicators.domain.kospi.entity.QKospi;
import com.chartnomy.indicators.domain.loan.entity.QLoanKr;
import com.chartnomy.indicators.domain.loan.entity.QLoanUs;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class QTrendingStockRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;


//	public QTrendingStockRepository(EntityManager em, JPAQueryFactory queryFactory){
	public QTrendingStockRepository(EntityManager em){
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}
}

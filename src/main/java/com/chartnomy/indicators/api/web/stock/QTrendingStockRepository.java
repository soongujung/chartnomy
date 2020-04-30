package com.chartnomy.indicators.api.web.stock;

import com.querydsl.jpa.impl.JPAQueryFactory;
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

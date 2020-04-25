package com.chartnomy.indicators.trending.index;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

/**
 * 코스피/나스닥, 환율, 금리(미국/한국) 차트를 위한 일별 데이터 조회 Repository
 */
@Repository
//@RequiredArgsConstructor
public class QTrendingIndexRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

//	public QTrendingIndexRepository(EntityManager em, JPAQueryFactory queryFactory){
	public QTrendingIndexRepository(EntityManager em){
			this.em = em;
			this.queryFactory = new JPAQueryFactory(em);
		}
	}
//	public QTrendingIndexRepository(EntityManager em, JPAQueryFactory queryFactory){

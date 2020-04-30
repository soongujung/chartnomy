package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.domain.kospi.entity.Kospi;
import com.chartnomy.indicators.domain.kospi.entity.QKospi;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

/**
 * 코스피/나스닥, 환율, 금리(미국/한국) 차트를 위한 일별 데이터 조회 Repository
 */
@Repository
//@RequiredArgsConstructor
public class QTrendingIndexRepositoryImpl implements QTrendingIndexRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

//	public QTrendingIndexRepository(EntityManager em, JPAQueryFactory queryFactory){
	public QTrendingIndexRepositoryImpl(EntityManager em){
			this.em = em;
			this.queryFactory = new JPAQueryFactory(em);
	}


	@Override
	public List<Kospi> selectKospi() {
		List<Kospi> fetch = queryFactory.selectFrom(QKospi.kospi).fetch();
		return fetch;
	}
}

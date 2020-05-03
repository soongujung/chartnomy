package com.chartnomy.indicators.api.web.trending.index;

import com.chartnomy.indicators.api.web.trending.index.dto.TrendingDto;
import com.chartnomy.indicators.domain.kospi.entity.Kospi;
import com.chartnomy.indicators.domain.kospi.entity.QKospi;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class QTrendingIndexRepositoryImplTest {

	@Autowired
	private EntityManager entityManager;

	private JPAQueryFactory queryFactory;

	@Autowired
	private QTrendingIndexRepository trendingIndexRepository;

	@BeforeEach
	public void before(){
		queryFactory = new JPAQueryFactory(entityManager);
	}

	@Test
	@DisplayName("단순 쿼리 동작 여부 테스트 (querydsl)")
	public void selectKospi() {
		List<Kospi> fetch = queryFactory.selectFrom(QKospi.kospi).fetch();
		System.out.println(fetch);
	}

	@Test
	@DisplayName("리포지터리 동작 여부 테스트 (repository)")
	public void selectKospiRepository(){
		List<TrendingDto> index = trendingIndexRepository.getDefaultTrendingResult();
		System.out.println(index);
	}
}
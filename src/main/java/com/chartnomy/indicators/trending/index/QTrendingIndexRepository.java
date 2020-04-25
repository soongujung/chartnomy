package com.chartnomy.indicators.trending.index;

import com.chartnomy.indicators.kospi.entity.Kospi;
import java.util.List;

public interface QTrendingIndexRepository {
	public List<Kospi> selectKospi();
}

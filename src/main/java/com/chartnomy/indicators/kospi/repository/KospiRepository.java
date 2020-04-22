package com.chartnomy.indicators.kospi.repository;

import com.chartnomy.indicators.kospi.entity.Kospi;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 데이터 CUD 를 위한 Data JPA Repository
 */
public interface KospiRepository extends JpaRepository<Kospi, String> {

}

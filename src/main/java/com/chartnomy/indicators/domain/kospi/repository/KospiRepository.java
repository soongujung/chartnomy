package com.chartnomy.indicators.domain.kospi.repository;

import com.chartnomy.indicators.domain.kospi.entity.Kospi;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 데이터 CUD 를 위한 Data JPA Repository
 */
public interface KospiRepository extends JpaRepository<Kospi, LocalDate> {

}

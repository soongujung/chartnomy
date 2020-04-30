package com.chartnomy.indicators.domain.axis.repository;

import com.chartnomy.indicators.domain.axis.entity.DateAxis;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateAxisRepository extends JpaRepository<DateAxis, LocalDate> {

}

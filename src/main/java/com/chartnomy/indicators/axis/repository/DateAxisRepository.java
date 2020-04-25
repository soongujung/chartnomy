package com.chartnomy.indicators.axis.repository;

import com.chartnomy.indicators.axis.entity.DateAxis;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateAxisRepository extends JpaRepository<DateAxis, LocalDate> {

}

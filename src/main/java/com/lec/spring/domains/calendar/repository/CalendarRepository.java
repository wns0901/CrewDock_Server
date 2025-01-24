package com.lec.spring.domains.calendar.repository;

import com.lec.spring.domains.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}

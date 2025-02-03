package com.lec.spring.domains.calendar.repository;

import com.lec.spring.domains.calendar.entity.Calendar;
import com.lec.spring.domains.calendar.repository.dsl.QCalendarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long>, QCalendarRepository {
    Calendar findByProjectId(Long projectId);
}

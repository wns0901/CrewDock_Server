package com.lec.spring.domains.calendar.repository;

import com.lec.spring.domains.calendar.entity.Calendar;
//import com.lec.spring.domains.calendar.repository.dsl.QCalendarRepository;
import com.lec.spring.domains.calendar.repository.dsl.QCalendarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long>, QCalendarRepository {
    Optional<Calendar> findByProjectIdAndId(Long projectId, Long calendarId);
}

package com.lec.spring.domains.calendar.repository.dsl;

import com.lec.spring.domains.calendar.entity.Calendar;

import java.util.List;

public interface QCalendarRepository {
    // 공휴일 + 개인 일정 + 본인이 속한 팀 일정 조회 >> 마이페이지 일정에서 확인 가능
    List<Calendar> findUserCalendar(Long userId, int year, int month);

    // 공휴일 + 해당 팀 일정 조회 >> 팀 프로젝트 일정에서만 확인 가능.
    List<Calendar> findProjectCalendar(Long projectId, int year, int month);
}

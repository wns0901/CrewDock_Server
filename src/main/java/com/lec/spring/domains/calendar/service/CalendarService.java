package com.lec.spring.domains.calendar.service;

import com.lec.spring.domains.calendar.entity.Calendar;

import java.util.List;

public interface CalendarService {
    // 공휴일 + 개인 일정 + 본인이 속한 모든 팀 일정
    List<Calendar> getUserCalendar(Long userId);

    // 공휴일 + 해당 팀 일정
    List<Calendar> getProjectCalendar(Long projectId);

    // 개인 일정 추가 >> 마이페이지 일정에서만 가능
    Calendar addPersonalEvent(Calendar calendar);

    // 개인 일정 수정 >> 마이페이지 일정에서만 가능
    Calendar updatePersonalEvent(Long calendarId, Calendar calendar);

    // 개인 일정 삭제 >> 마이페이지 일정에서만 가능
    int deletePersonalEvent(Long calendarId);

    // 해당 팀 일정 추가 >> 해당 팀 프로젝트에서만 가능
    Calendar addProjectEvent(Calendar calendar);

    // 해당 팀 일정 수정 >> 해당 팀 프로젝트에서만 가능
    Calendar updateProjectEvent(Long calendarId, Calendar calendar);

    // 해당 팀 일정 삭제 >> 해당 팀 프로젝트에서만 가능
    int deleteProjectEvent(Long calendarId);
}

package com.lec.spring.domains.calendar.service;

import com.lec.spring.domains.calendar.dto.CalendarDTO;
import com.lec.spring.domains.project.entity.Project;

import java.util.List;

public interface CalendarService {
    // 공휴일 + 개인 일정 + 본인이 속한 모든 팀 일정
    List<CalendarDTO> getUserCalendar(Long userId);

    // 공휴일 + 해당 팀 일정
    List<CalendarDTO> getProjectCalendar(Long projectId);

    // 개인 일정 추가 >> 마이페이지 일정에서만 가능
    CalendarDTO addPersonalEvent(Long userId, CalendarDTO calendarDTO);

    // 개인 일정 상세보기
    CalendarDTO detailCalendar(Long calendarId);

    // 개인 일정 수정 >> 마이페이지 일정에서만 가능
    CalendarDTO updatePersonalEvent(Long calendarId, CalendarDTO calendarDTO);

    // 개인 일정 삭제 >> 마이페이지 일정에서만 가능
    int deletePersonalEvent(Long userId, Long calendarId);

    // 해당 팀 일정 추가 >> 해당 팀 프로젝트에서만 가능
    CalendarDTO addProjectEvent(Long projectId, CalendarDTO calendarDTO);

    // 해당 팀 일정 상세보기 >> 해당 팀 프로젝트에서만 가능
    CalendarDTO detailProjectEvent(Long projectId, Long calendarId);

    // 해당 팀 일정 수정 >> 해당 팀 프로젝트에서만 가능
    CalendarDTO updateProjectEvent(Long calendarId, CalendarDTO calendarDTO);

    // 해당 팀 일정 삭제 >> 해당 팀 프로젝트에서만 가능
    int deleteProjectEvent(Long projectId, Long calendarId);
}

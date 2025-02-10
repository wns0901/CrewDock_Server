package com.lec.spring.domains.calendar.controller;

import com.lec.spring.domains.calendar.dto.CalendarDTO;
import com.lec.spring.domains.calendar.entity.Calendar;
import com.lec.spring.domains.calendar.service.CalendarService;
import com.lec.spring.domains.project.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/calendars")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    // 개인 일정 + 팀 일정 + 공휴일 조회
    // /calendars?[userId]
    @GetMapping
    public ResponseEntity<List<CalendarDTO>> getUserCalendar(@RequestParam Long userId) {
        List<CalendarDTO> userCalendar = calendarService.getUserCalendar(userId);
        return ResponseEntity.ok(userCalendar);
    }

    // 팀 프로젝트 일정 + 공휴일 조회
    // /calendars/project?[projectId]
    @GetMapping("/project")
    public ResponseEntity<List<CalendarDTO>> getProjectCalendar(Long userId, @RequestParam Long projectId) {
        List<CalendarDTO> projectCalendar = calendarService.getProjectCalendar(userId, projectId);
        return ResponseEntity.ok(projectCalendar);
    }

    // 개인 일정 추가
    // /calenders?[userId]
    @PostMapping
    public ResponseEntity<Calendar> addPersonalEvent(@RequestParam Long userId, @RequestBody Calendar calendar) {
        Calendar createdCalendar = calendarService.addPersonalEvent(userId, calendar);
        return ResponseEntity.ok(createdCalendar);
    }

    // 개인 일정 수정
    // /calenders
    @PatchMapping("/{calendarId}")
    public ResponseEntity<Calendar> updatePersonalEvent(@PathVariable Long calendarId,
                                                        @RequestBody Calendar calendar) {
        Calendar updatedCalendar = calendarService.updatePersonalEvent(calendarId, calendar);
        return ResponseEntity.ok(updatedCalendar);
    }

    // 개인 일정 삭제
    // /calenders?{userId}&{calendarId}
    @DeleteMapping
    public ResponseEntity<Void> deletePersonalEvent(@RequestParam Long userId, @RequestParam Long calendarId) {
        calendarService.deletePersonalEvent(userId, calendarId);
        return ResponseEntity.noContent().build();
    }

    // 팀 일정 추가
    // /calendars/project?[projectId]
    @PostMapping("/project")
    public ResponseEntity<Calendar> addProjectEvent(@RequestParam Project projectId, @RequestBody Calendar calendar) {
        Calendar createdCalendar = calendarService.addProjectEvent(projectId, calendar);
        return ResponseEntity.ok(createdCalendar);
    }

    // 팀 일정 수정
    // /calendars/project
    @PatchMapping("/project/{calendarId}")
    public ResponseEntity<Calendar> updateProjectEvent(@PathVariable Long calendarId,
                                                       @RequestBody Calendar calendar) {
        Calendar updatedCalendar = calendarService.updateProjectEvent(calendarId, calendar);
        return ResponseEntity.ok(updatedCalendar);
    }

    // 팀 일정 삭제
    // /calendars/project?[projectId]&[calendarId]
    @DeleteMapping("/project")
    public ResponseEntity<Void> deleteProjectEvent(@RequestParam Long projectId, @RequestParam Long calendarId) {
        calendarService.deleteProjectEvent(projectId, calendarId);
        return ResponseEntity.noContent().build();
    }

    // 일정 상세보기 -> 팀, 개인 모두 사용
    // /calendars/{calendarId}
    @GetMapping("/{calendarId}")
    public CalendarDTO getCalendar(@PathVariable Long calendarId) {
        return calendarService.detailCalendar(calendarId);
    }
}

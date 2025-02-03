package com.lec.spring.domains.calendar.controller;

import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import com.lec.spring.domains.calendar.entity.Calendar;
import com.lec.spring.domains.calendar.service.CalendarService;
import com.lec.spring.domains.calendar.service.HolidaysService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calendars")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    // 개인 일정 + 팀 일정 + 공휴일 조회
    @GetMapping
    public ResponseEntity<List<Calendar>> getUserCalendar(@RequestParam Long userId) {
        List<Calendar> userCalendar = calendarService.getUserCalendar(userId);
        return ResponseEntity.ok(userCalendar);
    }

    // 팀 프로젝트 일정 + 공휴일 조회
    @GetMapping("/project")
    public ResponseEntity<List<Calendar>> getProjectCalendar(@RequestParam Long projectId) {
        List<Calendar> projectCalendar = calendarService.getProjectCalendar(projectId);
        return ResponseEntity.ok(projectCalendar);
    }

    // 개인 일정 추가
    @PostMapping
    public ResponseEntity<Calendar> addPersonalEvent(@RequestBody Calendar calendar) {
        Calendar createdCalendar = calendarService.addPersonalEvent(calendar);
        return ResponseEntity.ok(createdCalendar);
    }

    // 개인 일정 수정
    @PatchMapping("/{calendarId}")
    public ResponseEntity<Calendar> updatePersonalEvent(@PathVariable Long calendarId,
                                                        @RequestBody Calendar calendar) {
        Calendar updatedCalendar = calendarService.updatePersonalEvent(calendarId, calendar);
        return ResponseEntity.ok(updatedCalendar);
    }

    // 개인 일정 삭제
    @DeleteMapping("/{calendarId}")
    public ResponseEntity<Void> deletePersonalEvent(@PathVariable Long calendarId) {
        calendarService.deletePersonalEvent(calendarId);
        return ResponseEntity.noContent().build();
    }

    // 팀 일정 추가
    @PostMapping("/project")
    public ResponseEntity<Calendar> addProjectEvent(@RequestBody Calendar calendar) {
        Calendar createdCalendar = calendarService.addProjectEvent(calendar);
        return ResponseEntity.ok(createdCalendar);
    }

    // 팀 일정 수정
    @PatchMapping("/project/{calendarId}")
    public ResponseEntity<Calendar> updateProjectEvent(@PathVariable Long calendarId,
                                                       @RequestBody Calendar calendar) {
        Calendar updatedCalendar = calendarService.updateProjectEvent(calendarId, calendar);
        return ResponseEntity.ok(updatedCalendar);
    }

    // 팀 일정 삭제
    @DeleteMapping("/project/{calendarId}")
    public ResponseEntity<Void> deleteProjectEvent(@PathVariable Long calendarId) {
        calendarService.deleteProjectEvent(calendarId);
        return ResponseEntity.noContent().build();
    }
}

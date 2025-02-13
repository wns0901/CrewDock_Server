package com.lec.spring.domains.calendar.controller;

import com.lec.spring.domains.calendar.dto.CalendarDTO;
import com.lec.spring.domains.calendar.service.CalendarService;
import com.lec.spring.domains.project.entity.Project;
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
    // /calendars?[userId]
    @GetMapping
    public ResponseEntity<List<CalendarDTO>> getUserCalendar(@RequestParam Long userId) {
        List<CalendarDTO> userCalendar = calendarService.getUserCalendar(userId);
        return ResponseEntity.ok(userCalendar);
    }

    // 팀 프로젝트 일정 + 공휴일 조회
    // /calendars/project?[projectId]
    @GetMapping("/project")
    public ResponseEntity<List<CalendarDTO>> getProjectCalendar(@RequestParam Long projectId) {
        List<CalendarDTO> projectCalendar = calendarService.getProjectCalendar(projectId);
        return ResponseEntity.ok(projectCalendar);
    }

    // 개인 일정 추가
    // /calenders?[userId]
    @PostMapping
    public ResponseEntity<CalendarDTO> addPersonalEvent(@RequestParam Long userId, @RequestBody CalendarDTO calendarDTO) {
        CalendarDTO createdCalendar = calendarService.addPersonalEvent(userId, calendarDTO);
        return ResponseEntity.ok(createdCalendar);
    }

    // 개인 일정 상세보기
    // /calendars/{calendarId}
    @GetMapping("/{calendarId}")
    public CalendarDTO getCalendar(@PathVariable Long calendarId) {
        return calendarService.detailCalendar(calendarId);
    }

    // 개인 일정 수정
    // /calenders/{calendarId}
    @PatchMapping("/{calendarId}")
    public ResponseEntity<CalendarDTO> updatePersonalEvent(@PathVariable Long calendarId,
                                                        @RequestBody CalendarDTO calendarDTO) {
        CalendarDTO updatedCalendar = calendarService.updatePersonalEvent(calendarId, calendarDTO);
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
    public ResponseEntity<CalendarDTO> addProjectEvent(@RequestParam Long projectId, @RequestBody CalendarDTO calendarDTO) {
        CalendarDTO createdCalendar = calendarService.addProjectEvent(projectId, calendarDTO);
        return ResponseEntity.ok(createdCalendar);
    }

    // 팀 일정 상세보기
    // /calendars/project?{projectId}&{calendarId}
    @GetMapping("/project/{projectId}/{calendarId}")
    public ResponseEntity<CalendarDTO> getProjectEvent(@PathVariable Long projectId,
                                                       @PathVariable Long calendarId) {
        CalendarDTO calendarDTO = calendarService.detailProjectEvent(projectId, calendarId);
        return ResponseEntity.ok(calendarDTO);
    }


    // 팀 일정 수정
    // /calendars/project/{projectId}&{calendarId}
    @PatchMapping("/project/{projectId}/{calendarId}")
    public ResponseEntity<CalendarDTO> updateProjectEvent(@PathVariable Long projectId,
                                                          @PathVariable Long calendarId,
                                                          @RequestBody CalendarDTO calendarDTO) {
        CalendarDTO updatedCalendar = calendarService.updateProjectEvent(projectId, calendarId, calendarDTO);
        return ResponseEntity.ok(updatedCalendar);
    }

    // 팀 일정 삭제
    // /calendars/project?{projectId}&{calendarId}
    @DeleteMapping("/project")
    public ResponseEntity<Void> deleteProjectEvent(@RequestParam Long projectId, @RequestParam Long calendarId) {
        calendarService.deleteProjectEvent(projectId, calendarId);
        return ResponseEntity.noContent().build();
    }

}

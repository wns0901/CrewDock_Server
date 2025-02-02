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
    private final HolidaysService holidaysService;

    // 개인 + 팀 일정 리스트 조회 (공휴일 포함)
    @GetMapping
    public ResponseEntity<List<Calendar>> getUserCalendar(@RequestParam Long userId,
                                                          @RequestParam int year,
                                                          @RequestParam int month) {
        List<Calendar> userCalendar = calendarService.getUserCalendar(userId, year, month);
        // 공휴일 리스트도 추가하여 반환
        List<HolidaysDTO> holidays = holidayService.getHolidays(year);

        // 공휴일 추가 및 캘린더 리스트 반환
        return ResponseEntity.ok(userCalendar);
    }

    // 개인 일정 등록
    @PostMapping
    public ResponseEntity<Calendar> addUserCalendar(@RequestParam Long userId,
                                                    @RequestBody Calendar calendar) {
        Calendar createdCalendar = calendarService.addUserCalendar(userId, calendar);
        return ResponseEntity.ok(createdCalendar);
    }

    // 개인 일정 수정
    @PatchMapping
    public ResponseEntity<Calendar> updateUserCalendar(@RequestBody Calendar calendar) {
        Calendar updatedCalendar = calendarService.updateUserCalendar(calendar);
        return ResponseEntity.ok(updatedCalendar);
    }

    // 개인 일정 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteUserCalendar(@RequestParam Long userId,
                                                   @RequestParam Long calendarId) {
        calendarService.deleteUserCalendar(userId, calendarId);
        return ResponseEntity.noContent().build();
    }

    // 프로젝트 일정 리스트 조회 (공휴일 포함)
    @GetMapping("/project")
    public ResponseEntity<List<Calendar>> getProjectCalendar(@RequestParam Long projectId,
                                                             @RequestParam int year,
                                                             @RequestParam int month) {
        List<Calendar> projectCalendar = calendarService.getProjectCalendar(projectId, year, month);
        List<HolidaysDTO> holidays = holidayService.getHolidays(year);

        // 공휴일 추가 및 프로젝트 일정 리스트 반환
        return ResponseEntity.ok(projectCalendar);
    }

    // 프로젝트 일정 등록
    @PostMapping("/project")
    public ResponseEntity<Calendar> addProjectCalendar(@RequestParam Long projectId,
                                                       @RequestBody Calendar calendar) {
        Calendar createdCalendar = calendarService.addProjectCalendar(projectId, calendar);
        return ResponseEntity.ok(createdCalendar);
    }

    // 프로젝트 일정 수정
    @PatchMapping("/project")
    public ResponseEntity<Calendar> updateProjectCalendar(@RequestBody Calendar calendar) {
        Calendar updatedCalendar = calendarService.updateProjectCalendar(calendar);
        return ResponseEntity.ok(updatedCalendar);
    }

    // 프로젝트 일정 삭제
    @DeleteMapping("/project")
    public ResponseEntity<Void> deleteProjectCalendar(@RequestParam Long projectId,
                                                      @RequestParam Long calendarId) {
        calendarService.deleteProjectCalendar(projectId, calendarId);
        return ResponseEntity.noContent().build();
    }
}

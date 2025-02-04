package com.lec.spring.domains.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 개인 일정 + 팀 일정 + 공휴일까지 같이 처리하는 DTO
public class CalendarDTO {
    private Long id;
    private Long userId;          // 개인 일정의 경우 userId 사용
    private Long projectId;       // 팀 일정의 경우 projectId 사용
    private String content;       // 일정 내용 (예: 회의, 공휴일 이름 등)
    private LocalDate startDate;  // 일정 시작일
    private LocalDate endDate;    // 일정 종료일
    private LocalTime startTime;  // 일정 시작 시간
    private LocalTime endTime;    // 일정 종료 시간
    private boolean isHoliday;    // 공휴일 여부
}

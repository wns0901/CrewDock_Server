package com.lec.spring.domains.calendar.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
// 공휴일 api 호출에 필요한 DTO
public class HolidaysDTO {
    private String dateKind;    // 날짜 유형
    private LocalDate date;     // 공휴일 날짜
    private String name;        // 공휴일 이름
}

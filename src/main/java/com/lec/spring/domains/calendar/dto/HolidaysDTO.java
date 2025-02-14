package com.lec.spring.domains.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 공휴일 api 호출에 필요한 DTO
public class HolidaysDTO {
    private String dateName;  // 공휴일 이름 (예: 설날, 크리스마스)
    private LocalDate locdate; // YYYY-MM-DD 형식의 날짜

    // String 타입의 YYYYMMDD 값을 LocalDate로 변환하는 생성자
    public HolidaysDTO(String dateName, String locdate) {
        this.dateName = dateName;
        this.locdate = (locdate != null && !locdate.isEmpty()) ?
                LocalDate.parse(locdate, DateTimeFormatter.ofPattern("yyyyMMdd")) : null;
    }

    // 특정 날짜가 공휴일인지 확인하는 메서드
    public boolean isHoliday(LocalDate date) {
        return this.locdate != null && this.locdate.equals(date);
    }

}

package com.lec.spring.domains.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
// 공휴일 api 호출에 필요한 DTO
public class HolidaysDTO {
    private String dateKind;    // 공휴일 종류
    private String dateName;    // 공휴일 이름 (예: 설날, 크리스마스)
    private LocalDate locdate;  // YYYYMMDD 형식의 날짜

    // Integer 타입의 YYYYMMDD 값을 LocalDate로 변환하는 생성자 추가
    public HolidaysDTO(String dateName, Integer locdate) {
        this.dateName = dateName;
        this.locdate = (locdate != null) ?
                LocalDate.parse(locdate.toString(), DateTimeFormatter.ofPattern("yyyyMMdd"))
                : null;
    }

}

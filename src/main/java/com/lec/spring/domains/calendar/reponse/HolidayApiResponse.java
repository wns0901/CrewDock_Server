package com.lec.spring.domains.calendar.reponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static net.minidev.asm.ConvertDate.convertToDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayApiResponse {
    @JsonProperty("response")
    private Response response;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        private Body body;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {
        private Items items;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Items {
        private List<Item> item;

        public List<HolidaysDTO> toHolidaysDTOList() {
            return item.stream()
                    .map(i -> new HolidaysDTO(i.getDateName(), convertToDate(i.getLocdate())))
                    .collect(Collectors.toList());
        }

        // YYYYMMDD 형식의 int 값을 LocalDate로 변환하는 메서드
        private LocalDate convertToDate(Integer locdate) {
            if (locdate == null) return null;
            String dateStr = locdate.toString();
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String dateName;  // 공휴일 이름 (예: 설날, 크리스마스)
        private Integer locdate;  // YYYYMMDD 형식의 날짜
    }
}

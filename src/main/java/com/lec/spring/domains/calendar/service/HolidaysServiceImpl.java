// HolidaysServiceImpl 클래스 수정
package com.lec.spring.domains.calendar.service;

import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import com.lec.spring.domains.calendar.reponse.HolidayApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HolidaysServiceImpl implements HolidaysService {
    private final RestTemplate restTemplate;

    @Value("${holiday.api.url}")
    private String holidayApiUrl;

    @Value("${holiday.api.key}")
    private String apiKey;

    @Override
    public List<HolidaysDTO> getHolidaysForCurrentMonth() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        // 이번 달의 공휴일 데이터를 필터링하여 가져옴
        List<HolidaysDTO> holidays = fetchHolidays(year, month);

        // 로깅: API로 받은 응답이 제대로 들어오는지 확인
        holidays.forEach(holiday -> log.info("공휴일 데이터: {} - {}", holiday.getDateName(), holiday.getLocdate()));

        return holidays.stream()
                .filter(holiday -> holiday.getLocdate() != null &&
                        holiday.getLocdate().getMonthValue() == month) // 이번 달에 해당하는 공휴일만 필터링
                .collect(Collectors.toList());
    }

    // 공휴일 API 호출 메서드 (년과 월을 기준으로 필터링)
    private List<HolidaysDTO> fetchHolidays(int year, int month) {
        String url = UriComponentsBuilder.fromHttpUrl(holidayApiUrl)
                .queryParam("solYear", year) // 연도 파라미터
                .queryParam("solMonth", String.format("%02d", month)) // 월 파라미터: 01, 02, ..., 12 형식으로
                .queryParam("_type", "json")
                .queryParam("ServiceKey", apiKey)
                .toUriString();

        try {
            HolidayApiResponse response = restTemplate.getForObject(url, HolidayApiResponse.class);

            // API 응답 확인
            if (response == null || response.getResponse() == null ||
                    response.getResponse().getBody() == null ||
                    response.getResponse().getBody().getItems() == null ||
                    response.getResponse().getBody().getItems().getItem() == null) {

                log.warn("공휴일 API 응답이 비어 있음");
                return Collections.emptyList();
            }

            // 로깅: API 응답의 구조를 로그로 찍어봄
            log.info("API 응답 구조: {}", response.getResponse().getBody());

            return response.getResponse().getBody().getItems().getItem().stream()
                    .map(item -> new HolidaysDTO(item.getDateName(), item.getLocdate()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("공휴일 API 호출 중 오류 발생: {}", e.getMessage());
            return Collections.emptyList(); // 예외 발생 시 빈 리스트 반환
        }
    }
}

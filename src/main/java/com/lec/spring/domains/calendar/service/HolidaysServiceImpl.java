package com.lec.spring.domains.calendar.service;

import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import com.lec.spring.domains.calendar.entity.Calendar;
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

        return fetchHolidays(year).stream()
                .filter(holiday -> holiday.getLocdate() != null &&
                        holiday.getLocdate().getYear() == year &&
                        holiday.getLocdate().getMonthValue() == month)
                .collect(Collectors.toList());
    }

    // 공휴일 API 호출 메서드
    private List<HolidaysDTO> fetchHolidays(int year) {
        String url = UriComponentsBuilder.fromHttpUrl(holidayApiUrl)
                .queryParam("solYear", year)
                .queryParam("_type", "json")
                .queryParam("ServiceKey", apiKey)
                .toUriString();

        try {
            HolidayApiResponse response = restTemplate.getForObject(url, HolidayApiResponse.class);

            // 🔹 API 응답이 null이면 빈 리스트 반환
            if (response == null || response.getResponse() == null ||
                    response.getResponse().getBody() == null ||
                    response.getResponse().getBody().getItems() == null ||
                    response.getResponse().getBody().getItems().getItem() == null) {

                log.warn("공휴일 API 응답이 비어 있음");
                return Collections.emptyList();
            }

            return response.getResponse().getBody().getItems().getItem().stream()
                    .map(item -> new HolidaysDTO(item.getDateName(), item.getLocdate()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("공휴일 API 호출 중 오류 발생: {}", e.getMessage());
            return Collections.emptyList(); // 예외 발생 시 빈 리스트 반환
        }
    }
}

// HolidaysServiceImpl 클래스 수정
package com.lec.spring.domains.calendar.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HolidaysServiceImpl implements HolidaysService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${holiday.api.url}")
    private String holidayApiUrl;

    @Value("${holiday.api.key}")
    private String apiKey;

    @Override
    public List<HolidaysDTO> getHolidaysForCurrentMonth() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        List<HolidaysDTO> holidays = fetchHolidays(year, month);

        holidays.forEach(holiday -> log.info("공휴일 데이터: {} - {}", holiday.getDateName(), holiday.getLocdate()));

        return holidays;
    }

    // 공휴일 API 호출 메서드 (년과 월을 기준으로 필터링)
    private List<HolidaysDTO> fetchHolidays(int year, int month) {
        String url = UriComponentsBuilder.fromHttpUrl(holidayApiUrl)
                .queryParam("serviceKey", apiKey)
                .queryParam("solYear", year)
                .queryParam("solMonth", String.format("%02d", month))
                .queryParam("_type", "json")
                .toUriString();

        // 요청 URL 로그 출력
        log.info("공휴일 API 요청 URL: {}", url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                log.warn("공휴일 API 응답이 비어 있음");
                return Collections.emptyList();
            }

            log.info("API 응답 JSON: {}", response.getBody());

            return parseHolidays(response.getBody());

        } catch (Exception e) {
            log.error("공휴일 API 호출 중 오류 발생: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<HolidaysDTO> parseHolidays(String jsonResponse) {
        List<HolidaysDTO> holidays = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode items = root.path("response").path("body").path("items").path("item");

            if (items.isArray()) {
                for (JsonNode item : items) {
                    String dateName = item.path("dateName").asText();
                    String locdate = item.path("locdate").asText(); // YYYYMMDD 문자열로 변환

                    holidays.add(new HolidaysDTO(dateName, locdate));
                }
            }
        } catch (Exception e) {
            log.error("JSON 파싱 오류: {}", e.getMessage());
        }
        return holidays;
    }
}

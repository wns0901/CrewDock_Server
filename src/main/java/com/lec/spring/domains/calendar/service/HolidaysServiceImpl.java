package com.lec.spring.domains.calendar.service;

import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import com.lec.spring.domains.calendar.reponse.HolidayApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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
    public List<HolidaysDTO> getHolidays(int year) {
        String url = UriComponentsBuilder.fromHttpUrl(holidayApiUrl)
                .queryParam("solYear", year)
                .queryParam("_type", "json")
                .queryParam("ServiceKey", apiKey)
                .toUriString();

        try {
            HolidayApiResponse response = restTemplate.getForObject(url, HolidayApiResponse.class);
            if (response != null && response.getResponse().getBody().getItems() != null) {
                return response.getResponse().getBody().getItems().toHolidaysDTOList();
            }
        } catch (Exception e) {
            log.error("공휴일 API 호출 중 오류 발생: {}", e.getMessage());
        }

        return List.of(); // 공휴일 정보가 없으면 빈 리스트 반환
    }
    }
}

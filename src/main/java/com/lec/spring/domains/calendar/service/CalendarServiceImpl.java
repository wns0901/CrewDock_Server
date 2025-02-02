package com.lec.spring.domains.calendar.service;

import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import com.lec.spring.domains.calendar.entity.Calendar;
import com.lec.spring.domains.calendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final HolidaysService holidaysService;

    @Override
    public List<Calendar> getUserCalendar(Long userId, int year, int month) {
        List<Calendar> userEvents = calendarRepository.findUserCalendar(userId, year, month);
        List<HolidaysDTO> holidays = holidaysService.getHolidays(year);

        List<Calendar> holidayEvents = holidays.stream()
                .map(holiday -> Calendar.builder()
                        .contnet(holiday.getName())
                        .startDate(holiday.getDate())
                        .endDate(holiday.getDate())
                        .build())
                .collect(Collectors.toList());

        userEvents.addAll(holidayEvents);
        return userEvents;
    }

    @Override
    public List<Calendar> getProjectCalendar(Long projectId, int year, int month) {
        return List.of();
    }

    @Override
    public Calendar addPersonalEvent(Calendar calendar) {
        return null;
    }

    @Override
    public Calendar updatePersonalEvent(Long calendarId, Calendar calendar) {
        return null;
    }

    @Override
    public int deletePersonalEvent(Long calendarId) {
        return 0;
    }

    @Override
    public Calendar addProjectEvent(Calendar calendar) {
        return null;
    }

    @Override
    public Calendar updateProjectEvent(Long calendarId, Calendar calendar) {
        return null;
    }

    @Override
    public int deleteProjectEvent(Long calendarId) {
        return 0;
    }
}

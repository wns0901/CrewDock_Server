package com.lec.spring.domains.calendar.service;

import com.lec.spring.domains.calendar.entity.Calendar;
import com.lec.spring.domains.calendar.repository.CalendarRepository;
import com.lec.spring.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final HolidaysService holidaysService;  // 공휴일 관련 Service 주입
    private final UserRepository userRepository;


    // 개인 일정 페이지에서 개인 일정 + 공휴일 + 본인이 소속된 모든 팀 일정 보여주기
    // 팀은 여러개 가능. 하지만 팀 일정 해당 페이지에서 수정 및 삭제 불가
    @Override
    @Transactional(readOnly = true)
    public List<Calendar> getUserCalendar(Long userId) {
        List<Calendar> userCalendar = calendarRepository.findUserCalendar(userId);
        userCalendar.addAll(convertHolidaysToCalendars()); // 캘린더에 공휴일 추가
        return userCalendar;
    }

    // 팀 일정 페이지에서 공휴일 + 해당 팀 일정 보여주기
    // 개인적인 일정들은 여기에 보이지 않음. 해당 팀에서 작성한 것만 보임
    @Override
    @Transactional(readOnly = true)
    public List<Calendar> getProjectCalendar(Long projectId) {
        List<Calendar> projectCalendar = calendarRepository.findProjectCalendar(projectId);
        projectCalendar.addAll(convertHolidaysToCalendars());

        return projectCalendar;
    }

    // 개인 일정 페이지에서 개인 일정 추가
    @Override
    public Calendar addPersonalEvent(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    // 개인 일정 페이지에서 개인 일정 수정
    @Override
    public Calendar updatePersonalEvent(Long calendarId, Calendar updateData) {
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다. ID: " + calendarId));

        calendar.setContnet(updateData.getContnet());
        calendar.setStartDate(updateData.getStartDate());
        calendar.setEndDate(updateData.getEndDate());
        calendar.setStartTime(updateData.getStartTime());
        calendar.setEndTime(updateData.getEndTime());

        return calendarRepository.save(calendar);
    }

    // 개인 일정 페이지에서 개인 일정 삭제
    @Override
    public int deletePersonalEvent(Long calendarId) {
        Optional<Calendar> calendar = calendarRepository.findById(calendarId);
        calendar.ifPresent(calendarRepository::delete);
        return calendar.isPresent() ? 1 : 0;
    }

    // 팀 일정 페이지에서 팀 일정 추가 -> 팀 멤버라면 누구든 가능
    @Override
    public Calendar addProjectEvent(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    // 팀 일정 페이지에서 팀 일정 수정 -> 팀 멤버라면 누구든 가능
    @Override
    public Calendar updateProjectEvent(Long calendarId, Calendar updateData) {
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다. ID: " + calendarId));

        calendar.setContnet(updateData.getContnet());
        calendar.setStartDate(updateData.getStartDate());
        calendar.setEndDate(updateData.getEndDate());
        calendar.setStartTime(updateData.getStartTime());
        calendar.setEndTime(updateData.getEndTime());

        return calendarRepository.save(calendar);
    }

    // 팀 일정 페이지에서 팀 일정 삭제 -> 팀 멤버라면 누구든 가능
    @Override
    public int deleteProjectEvent(Long calendarId) {
        Optional<Calendar> calendar = calendarRepository.findById(calendarId);
        calendar.ifPresent(calendarRepository::delete);
        return calendar.isPresent() ? 1 : 0;
    }

    // 공휴일 데이터를 Calendar 엔티티로 변환
    private List<Calendar> convertHolidaysToCalendars() {
        return holidaysService.getHolidaysForCurrentMonth().stream()
                .map(holiday -> Calendar.builder()
                        .contnet(holiday.getDateName()) // 공휴일 이름
                        .startDate(holiday.getLocdate()) // 공휴일 날짜
                        .endDate(holiday.getLocdate()) // 공휴일 날짜 (단일 날짜 이벤트)
                        .build())
                .collect(Collectors.toList());
    }
}

package com.lec.spring.domains.calendar.repository.dsl;

import com.lec.spring.domains.calendar.dto.CalendarDTO;
import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import com.lec.spring.domains.calendar.entity.QCalendar;
import com.lec.spring.domains.calendar.service.HolidaysService;
import com.lec.spring.domains.project.entity.QProjectMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.lec.spring.domains.calendar.entity.QCalendar.calendar;

@Repository
@RequiredArgsConstructor
public class QCalendarRepositoryImpl implements QCalendarRepository {
    private final JPAQueryFactory queryFactory;
    private final HolidaysService holidaysService;

    @Override
    public List<CalendarDTO> findUserCalendar(Long userId) {
        QCalendar calendar = QCalendar.calendar;

        // 공휴일 데이터 조회 (API 호출)
        List<HolidaysDTO> holidays = holidaysService.getHolidaysForCurrentMonth();

        // 개인 일정 및 팀 일정 데이터 조회 (QueryDSL)
        List<CalendarDTO> userCalendars = queryFactory
                .select(
                        calendar.id,
                        calendar.user.id.as("userId"),
                        calendar.project.id.as("projectId"),
                        calendar.content,
                        calendar.startDate,
                        calendar.endDate,
                        calendar.startTime,
                        calendar.endTime
                )
                .from(calendar)
                .where(calendar.user.id.eq(userId)
                        .or(calendar.project.id.in(
                                queryFactory.select(QProjectMember.projectMember.userId) // 사용자가 속한 프로젝트 조회
                                        .from(QProjectMember.projectMember)
                                        .where(QProjectMember.projectMember.userId.eq(userId))
                        ))).fetch()
                .stream()
                .map(tuple -> new CalendarDTO(
                        tuple.get(0, Long.class), // ID
                        tuple.get(1, Long.class), // userId
                        tuple.get(2, Long.class), // projectId
                        tuple.get(3, String.class), // content
                        tuple.get(4, LocalDate.class), // startDate
                        tuple.get(5, LocalDate.class), // endDate
                        tuple.get(6, LocalTime.class), // startTime
                        tuple.get(7, LocalTime.class), // endTime
                        false // 일반 일정은 공휴일 여부가 false
                ))
                .collect(Collectors.toList());

        // 공휴일 데이터를 일정 리스트에 추가
        holidays.forEach(holiday -> {
            // 공휴일이 해당 날짜와 일치하는지 확인
            boolean isHoliday = holiday.isHoliday(); // dateKind가 있는지 확인

            // 주어진 날짜가 공휴일이면 true, 아니면 false
            if (isHoliday || holiday.isHoliday(LocalDate.now())) {
                CalendarDTO holidayCalendar = new CalendarDTO(
                        null, // ID는 null, 공휴일 데이터에는 없으므로
                        userId, // 공휴일은 사용자와 연관이 있으므로 userId를 전달
                        null, // 프로젝트 ID는 필요 없음
                        holiday.getDateName(), // 공휴일 이름
                        holiday.getLocdate(), // 시작일 = 공휴일 날짜
                        holiday.getLocdate(), // 종료일 = 공휴일 날짜
                        null, // 시작 시간은 null
                        null, // 종료 시간은 null
                        true // 공휴일 여부를 true로 표시
                );
                userCalendars.add(holidayCalendar);
            }
        });

        return userCalendars;
    }

    @Override
    public List<CalendarDTO> findProjectCalendar(Long userId, Long projectId) {
        QCalendar calendar = QCalendar.calendar;

        // 공휴일 데이터 조회 (API 호출)
        List<HolidaysDTO> holidays = holidaysService.getHolidaysForCurrentMonth();

        // 프로젝트 일정 조회 (QueryDSL)
        List<CalendarDTO> projectCalendars = queryFactory
                .select(
                        calendar.id,
                        calendar.user.id.as("userId"),
                        calendar.project.id.as("projectId"),
                        calendar.content,
                        calendar.startDate,
                        calendar.endDate,
                        calendar.startTime,
                        calendar.endTime
                )
                .from(calendar)
                .where(calendar.project.id.eq(projectId))
                .fetch()
                .stream()
                .map(tuple -> new CalendarDTO(
                        tuple.get(0, Long.class), // ID
                        tuple.get(1, Long.class), // userId
                        tuple.get(2, Long.class), // projectId
                        tuple.get(3, String.class), // content
                        tuple.get(4, LocalDate.class), // startDate
                        tuple.get(5, LocalDate.class), // endDate
                        tuple.get(6, LocalTime.class), // startTime
                        tuple.get(7, LocalTime.class), // endTime
                        false // 프로젝트 일정은 공휴일 여부가 false
                ))
                .collect(Collectors.toList());

        // 공휴일 데이터를 일정 리스트에 추가
        holidays.forEach(holiday -> {
            // 공휴일이 해당 날짜와 일치하는지 확인
            boolean isHoliday = holiday.isHoliday(); // dateKind가 있는지 확인

            // 주어진 날짜가 공휴일이면 true, 아니면 false
            if (isHoliday || holiday.isHoliday(LocalDate.now())) {
                CalendarDTO holidayCalendar = new CalendarDTO(
                        null, // ID는 null, 공휴일 데이터에는 없으므로
                        userId, // 팀 일정이므로 userId 포함
                        projectId, // 프로젝트 ID 사용
                        holiday.getDateName(), // 공휴일 이름
                        holiday.getLocdate(), // 시작일 = 공휴일 날짜
                        holiday.getLocdate(), // 종료일 = 공휴일 날짜
                        null, // 시작 시간은 null
                        null, // 종료 시간은 null
                        true // 공휴일 여부를 true로 표시
                );
                projectCalendars.add(holidayCalendar);
            }
        });

        return projectCalendars;
    }

    // 오늘의 나의 일정 조회
    @Override
    public List<CalendarDTO> todaysCalendar(LocalDate today) {
        return queryFactory.select(Projections.bean(CalendarDTO.class,
                        calendar.id,
                        calendar.user.id.as("userId"),
                        calendar.project.id.as("projectId"),
                        calendar.content,
                        calendar.startDate,
                        calendar.endDate,
                        calendar.startTime,
                        calendar.endTime
                        )) // CalendarDTO로 변환
                .from(calendar)
                .where(calendar.startDate.eq(today) // 오늘 시작일인 일정
                        .or(calendar.endDate.eq(today))) // 오늘 종료일인 일정
                .fetch();
    }
}

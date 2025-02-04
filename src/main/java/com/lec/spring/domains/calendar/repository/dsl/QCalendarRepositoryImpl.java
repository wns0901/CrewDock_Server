package com.lec.spring.domains.calendar.repository.dsl;

import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import com.lec.spring.domains.calendar.entity.Calendar;
import com.lec.spring.domains.calendar.entity.QCalendar;
import com.lec.spring.domains.calendar.service.HolidaysService;
import com.lec.spring.domains.project.entity.QProjectMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QCalendarRepositoryImpl implements QCalendarRepository {
    private final JPAQueryFactory queryFactory;
    private final HolidaysService holidaysService;

    @Override
    public List<Calendar> findUserCalendar(Long userId) {
        QCalendar calendar = QCalendar.calendar;
        QProjectMember projectMember = QProjectMember.projectMember;

        // 공휴일 데이터 조회 (API 호출)
        List<HolidaysDTO> holidays = holidaysService.getHolidaysForCurrentMonth();

        // 일반 일정 데이터 조회 (QueryDSL)
        List<Calendar> userCalendars = queryFactory
                .selectFrom(calendar)
                .where(calendar.user.id.eq(userId)
                        .or(calendar.project.id.in(
                                queryFactory.select(projectMember.project.id) // ✅ 사용자가 속한 프로젝트 조회
                                        .from(projectMember)
                                        .where(projectMember.userId.eq(userId))
                        )))
                .fetch();

        // 공휴일 데이터를 일정 리스트에 추가
        holidays.forEach(holiday -> {
            Calendar holidayCalendar = Calendar.builder()
                    .content(holiday.getDateName()) // 공휴일 이름 저장
                    .startDate(holiday.getLocdate())
                    .endDate(holiday.getLocdate())
                    .build();
            userCalendars.add(holidayCalendar);
        });

        return userCalendars;
    }

    @Override
    public List<Calendar> findProjectCalendar(Long projectId) {
        QCalendar calendar = QCalendar.calendar;

        // 공휴일 데이터 조회 (API 호출)
        List<HolidaysDTO> holidays = holidaysService.getHolidaysForCurrentMonth();

        // 프로젝트 일정 조회
        List<Calendar> projectCalendars = queryFactory
                .selectFrom(calendar)
                .where(calendar.project.id.eq(projectId))
                .fetch();

        // 공휴일 데이터를 일정 리스트에 추가
        holidays.forEach(holiday -> {
            Calendar holidayCalendar = Calendar.builder()
                    .content(holiday.getDateName())
                    .startDate(holiday.getLocdate())
                    .endDate(holiday.getLocdate())
                    .build();
            projectCalendars.add(holidayCalendar);
        });

        return projectCalendars;
    }
}

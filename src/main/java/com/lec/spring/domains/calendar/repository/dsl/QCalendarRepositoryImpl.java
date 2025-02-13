package com.lec.spring.domains.calendar.repository.dsl;

import com.lec.spring.domains.calendar.dto.CalendarDTO;
import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import com.lec.spring.domains.calendar.entity.QCalendar;
import com.lec.spring.domains.calendar.service.HolidaysService;
import com.lec.spring.domains.project.entity.QProjectMember;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.lec.spring.domains.calendar.entity.QCalendar.calendar;
import static com.lec.spring.domains.project.entity.QProject.project;

@Repository
@RequiredArgsConstructor
public class QCalendarRepositoryImpl implements QCalendarRepository {
    private final JPAQueryFactory queryFactory;
    private final HolidaysService holidaysService;

    @Override
    public List<CalendarDTO> findUserCalendar(Long userId, List<Long> projectIds) {
        QProjectMember projectMember = QProjectMember.projectMember;

        List<HolidaysDTO> holidays = holidaysService.getHolidaysForCurrentMonth();
        NumberExpression<Integer> duration = Expressions.numberTemplate(Integer.class, "DATEDIFF({0}, {1})", calendar.endDate, calendar.startDate);

        // 본인의 개인 일정 + 본인이 속한 팀 일정 가져오기
        List<CalendarDTO> userCalendars = queryFactory
                .select(Projections.constructor(CalendarDTO.class,
                        calendar.id,
                        calendar.user.id.as("userId"),
                        calendar.project.id.as("projectId"),
                        calendar.content,
                        calendar.startDate,
                        calendar.endDate,
                        calendar.startTime,
                        calendar.endTime,
                        Expressions.constant(false) // 공휴일 여부 false
                ))
                .from(calendar)
                .where(calendar.user.id.eq(userId)
                        .or(calendar.project.id.in(projectIds)))
                .orderBy(calendar.startDate.asc(), calendar.startTime.asc().nullsLast(), duration.desc())
                .fetch();

        // 공휴일 추가
        holidays.forEach(holiday -> userCalendars.add(new CalendarDTO(null, userId, null, holiday.getDateName(), holiday.getLocdate(), holiday.getLocdate(), null, null, true)));

        return userCalendars;
    }

    @Override
    public List<CalendarDTO> findProjectCalendar(Long projectId) {
        List<HolidaysDTO> holidays = holidaysService.getHolidaysForCurrentMonth();

        List<CalendarDTO> projectCalendars = queryFactory
                .select(Projections.constructor(CalendarDTO.class,
                        calendar.id,
                        calendar.user.id.as("userId"),
                        calendar.project.id.as("projectId"),
                        calendar.content,
                        calendar.startDate,
                        calendar.endDate,
                        calendar.startTime,
                        calendar.endTime,
                        Expressions.constant(false) // 공휴일 여부 false
                ))
                .from(calendar)
                .where(calendar.project.id.eq(projectId))
                .fetch();

        // 공휴일 추가
        holidays.forEach(holiday -> projectCalendars.add(new CalendarDTO(null, null, projectId, holiday.getDateName(), holiday.getLocdate(), holiday.getLocdate(), null, null, true)));

        return projectCalendars;
    }

}

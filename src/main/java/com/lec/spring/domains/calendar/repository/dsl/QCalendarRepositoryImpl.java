package com.lec.spring.domains.calendar.repository.dsl;

import com.lec.spring.domains.calendar.entity.Calendar;
import com.lec.spring.domains.calendar.entity.QCalendar;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QCalendarRepositoryImpl implements QCalendarRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Calendar> findUserCalendar(Long userId) {
        QCalendar calendar = QCalendar.calendar;

        return queryFactory
                .selectFrom(calendar)
                .where(calendar.user.id.eq(userId)
                        .or(calendar.isPublicHoliday.isTrue())  // 공휴일 포함
                        .or(calendar.project.members.any().id.eq(userId))) // 팀 일정 포함
                .fetch();
    }

    @Override
    public List<Calendar> findProjectCalendar(Long projectId) {
        QCalendar calendar = QCalendar.calendar;

        return queryFactory
                .selectFrom(calendar)
                .where(calendar.project.id.eq(projectId)
                        .or(calendar.isPublicHoliday.isTrue())) // 공휴일 포함
                .fetch();
    }
}

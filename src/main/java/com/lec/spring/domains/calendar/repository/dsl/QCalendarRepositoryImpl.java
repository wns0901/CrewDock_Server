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
    public List<Calendar> findUserCalendar(Long userId, int year, int month) {
        QCalendar calendar = QCalendar.calendar;
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        return queryFactory
                .selectFrom(calendar)
                .where(calendar.user.id.eq(userId)
                        .and(calendar.startDate.between(startDate, endDate)))
                .fetch();
    }

    @Override
    public List<Calendar> findProjectCalendar(Long projectId, int year, int month) {
        QCalendar calendar = QCalendar.calendar;
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        return queryFactory
                .selectFrom(calendar)
                .where(calendar.project.id.eq(projectId)
                        .and(calendar.startDate.between(startDate, endDate)))
                .fetch();
    }
}

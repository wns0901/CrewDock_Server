package com.lec.spring.domains.calendar.repository.dsl;

import com.lec.spring.domains.calendar.dto.CalendarDTO;
import com.lec.spring.domains.calendar.repository.CalendarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QCalendarRepositoryImplTest {

    @Autowired
    private CalendarRepository calendarRepository;

    @Test
    public void calendarTest() {
        List<CalendarDTO> calendarDTOList = calendarRepository.findUserCalendar(2L);
        calendarDTOList.forEach(System.out::println);
    }
}
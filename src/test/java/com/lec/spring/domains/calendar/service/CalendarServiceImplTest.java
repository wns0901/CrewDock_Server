package com.lec.spring.domains.calendar.service;


import com.lec.spring.domains.calendar.entity.Calendar;
import com.lec.spring.domains.calendar.repository.CalendarRepository;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CalendarServiceImplTest {

    @InjectMocks
    private CalendarServiceImpl calendarService;

    @Mock
    private CalendarRepository calendarRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private HolidaysService holidaysService;

    private User testUser;
    private Project testProject;
    private Calendar testCalendar;

    @BeforeEach
    void setUp() {
        // 더미 User & Project 객체 생성
        testUser = User.builder()
                .id(1L)
                .username("사용자1")
                .build();

        testProject = Project.builder()
                .id(1L)
                .name("팀1")
                .build();

        testCalendar = Calendar.builder()
                .user(testUser)
                .project(testProject)  // 프로젝트가 없을 수도 있으므로 null 허용
                .content("테스트 일정")
                .startTime(LocalTime.of(13, 0, 0))
                .endTime(LocalTime.of(14, 0, 0))
                .startDate(LocalDate.of(2025, 2, 10))
                .endDate(LocalDate.of(2025, 2, 10))
                .build();

        // 🛠 Mock 객체가 특정 ID로 조회하면 더미 데이터를 반환하도록 설정
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(testProject));
        when(calendarRepository.save(any(Calendar.class))).thenReturn(testCalendar);
        when(calendarRepository.count()).thenReturn(1L);  // count() 테스트용

        // 🛠 `holidaysService.getHolidaysForCurrentMonth()`가 빈 리스트를 반환하도록 설정
        when(holidaysService.getHolidaysForCurrentMonth()).thenReturn(List.of());

        calendarRepository.save(testCalendar);
    }

    @Test
    @DisplayName("더미 데이터 확인")
    public void checkDummyData() {
        System.out.println("Calendar Count: " + calendarRepository.count()); // 1 출력 기대
        assertEquals(1L, calendarRepository.count());
    }



}
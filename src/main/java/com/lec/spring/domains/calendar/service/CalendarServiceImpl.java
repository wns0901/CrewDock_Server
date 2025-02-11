package com.lec.spring.domains.calendar.service;

import com.lec.spring.domains.calendar.dto.CalendarDTO;
import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import com.lec.spring.domains.calendar.entity.Calendar;
import com.lec.spring.domains.calendar.repository.CalendarRepository;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lec.spring.domains.project.entity.QProject.project;

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
    public List<CalendarDTO> getUserCalendar(Long userId) {
        // 일반 일정 조회
        List<CalendarDTO> userCalendar = calendarRepository.findUserCalendar(userId).stream()
                .map(calendar -> new CalendarDTO(
                        calendar.getId(),
                        calendar.getUserId(),
                        calendar.getProjectId(),
                        calendar.getContent(),
                        calendar.getStartDate(),
                        calendar.getEndDate(),
                        calendar.getStartTime(),
                        calendar.getEndTime(),
                        true
                ))
                .collect(Collectors.toList());

        // 공휴일 추가
        List<CalendarDTO> holidays = convertHolidaysToCalendarDTOs(holidaysService.getHolidaysForCurrentMonth());
        userCalendar.addAll(holidays); // 공휴일 추가

        return userCalendar;
    }

    // 팀 일정 페이지에서 공휴일 + 해당 팀 일정 보여주기
    // 개인적인 일정들은 여기에 보이지 않음. 해당 팀에서 작성한 것만 보임
    @Override
    @Transactional(readOnly = true)
    public List<CalendarDTO> getProjectCalendar(Long userId, Long projectId) {
        // 프로젝트 일정 조회
        List<CalendarDTO> projectCalendar = calendarRepository.findProjectCalendar(userId, projectId).stream()
                .map(calendar -> new CalendarDTO(
                        calendar.getId(),
                        calendar.getUserId(),
                        calendar.getProjectId(),
                        calendar.getContent(),
                        calendar.getStartDate(),
                        calendar.getEndDate(),
                        calendar.getStartTime(),
                        calendar.getEndTime(),
                        false // 공휴일 여부는 기본 false로 설정
                ))
                .collect(Collectors.toList());

        // 공휴일 추가
        List<CalendarDTO> holidays = convertHolidaysToCalendarDTOs(holidaysService.getHolidaysForCurrentMonth());
        projectCalendar.addAll(holidays); // 공휴일 추가

        return projectCalendar;
    }

    // 개인 일정 페이지에서 개인 일정 추가
    @Override
    public CalendarDTO addPersonalEvent(Long userId, CalendarDTO calendarDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. ID: " + userId));

        // Calendar Entity 생성
        Calendar calendar = Calendar.builder()
                .user(user)
                .content(calendarDTO.getContent())
                .startDate(calendarDTO.getStartDate())
                .endDate(calendarDTO.getEndDate())
                .startTime(calendarDTO.getStartTime())
                .endTime(calendarDTO.getEndTime())
                .build();

        Calendar savedCalendar = calendarRepository.save(calendar);

        // CalendarDTO로 변환 후 반환
        return new CalendarDTO(
                savedCalendar.getId(),
                savedCalendar.getUser().getId(),
                null,
                savedCalendar.getContent(),
                savedCalendar.getStartDate(),
                savedCalendar.getEndDate(),
                savedCalendar.getStartTime(),
                savedCalendar.getEndTime(),
                false
        );
    }

    // 개인 일정 페이지에서 개인 일정 수정
    @Override
    public CalendarDTO updatePersonalEvent(Long calendarId, CalendarDTO updateData) {
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다. ID: " + calendarId));

        // Update Calendar Entity
        calendar.setContent(updateData.getContent());
        calendar.setStartDate(updateData.getStartDate());
        calendar.setEndDate(updateData.getEndDate());
        calendar.setStartTime(updateData.getStartTime());
        calendar.setEndTime(updateData.getEndTime());

        Calendar updatedCalendar = calendarRepository.save(calendar);

        // CalendarDTO로 변환 후 반환
        return new CalendarDTO(
                updatedCalendar.getId(),
                updatedCalendar.getUser().getId(),
                null,
                updatedCalendar.getContent(),
                updatedCalendar.getStartDate(),
                updatedCalendar.getEndDate(),
                updatedCalendar.getStartTime(),
                updatedCalendar.getEndTime(),
                false
        );
    }

    // 개인 일정 페이지에서 개인 일정 삭제
    @Override
    public int deletePersonalEvent(Long userId, Long calendarId) {
        Optional<Calendar> calendarOpt = calendarRepository.findById(calendarId);
        if (calendarOpt.isPresent()) {
            Calendar calendar = calendarOpt.get();
            if (!calendar.getUser().getId().equals(userId)) {
                throw new IllegalArgumentException("본인의 일정만 삭제할 수 있습니다.");
            }
            calendarRepository.delete(calendar);
            return 1;
        }
        return 0;
    }

    // 팀 일정 페이지에서 팀 일정 추가 -> 팀 멤버라면 누구든 가능
    @Override
    public CalendarDTO addProjectEvent(Project projectId, CalendarDTO calendarDTO) {
        Calendar calendar = Calendar.builder()
                .project(projectId)
                .content(calendarDTO.getContent())
                .startDate(calendarDTO.getStartDate())
                .endDate(calendarDTO.getEndDate())
                .startTime(calendarDTO.getStartTime())
                .endTime(calendarDTO.getEndTime())
                .build();

        Calendar savedCalendar = calendarRepository.save(calendar);

        // CalendarDTO로 변환 후 반환
        return new CalendarDTO(
                savedCalendar.getId(),
                null,
                savedCalendar.getProject().getId(),
                savedCalendar.getContent(),
                savedCalendar.getStartDate(),
                savedCalendar.getEndDate(),
                savedCalendar.getStartTime(),
                savedCalendar.getEndTime(),
                false
        );
    }

    // 팀 일정 페이지에서 팀 일정 수정 -> 팀 멤버라면 누구든 가능
    @Override
    public CalendarDTO updateProjectEvent(Long calendarId, CalendarDTO calendarDTO) {
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다. ID: " + calendarId));

        // Update Calendar Entity
        calendar.setContent(calendarDTO.getContent());
        calendar.setStartDate(calendarDTO.getStartDate());
        calendar.setEndDate(calendarDTO.getEndDate());
        calendar.setStartTime(calendarDTO.getStartTime());
        calendar.setEndTime(calendarDTO.getEndTime());

        Calendar updatedCalendar = calendarRepository.save(calendar);

        // CalendarDTO로 변환 후 반환
        return new CalendarDTO(
                updatedCalendar.getId(),
                null,
                updatedCalendar.getProject().getId(),
                updatedCalendar.getContent(),
                updatedCalendar.getStartDate(),
                updatedCalendar.getEndDate(),
                updatedCalendar.getStartTime(),
                updatedCalendar.getEndTime(),
                false
        );
    }

    // 팀 일정 페이지에서 팀 일정 삭제 -> 팀 멤버라면 누구든 가능
    @Override
    public int deleteProjectEvent(Long projectId, Long calendarId) {
        Optional<Calendar> calendarOpt = calendarRepository.findById(calendarId);

        if (calendarOpt.isPresent()) {
            Calendar calendar = calendarOpt.get();
            if (!calendar.getProject().getId().equals(projectId)) {
                throw new IllegalArgumentException("해당 팀의 일정만 삭제할 수 있습니다.");
            }
            calendarRepository.delete(calendar);
            return 1;
        }
        return 0;
    }

    @Override
    public CalendarDTO detailCalendar(Long calendarId) {
        // 일정 ID에 해당하는 일정 조회
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));

        // 필요한 정보만을 CalendarDTO로 변환하여 반환
        return new CalendarDTO(
                calendar.getId(),
                calendar.getContent(),
                calendar.getStartDate(),
                calendar.getStartTime(),
                calendar.getEndDate(),
                calendar.getEndTime()
        );
    }

    // 공휴일 데이터를 CalendarDTO로 변환
    private List<CalendarDTO> convertHolidaysToCalendarDTOs(List<HolidaysDTO> holidaysForCurrentMonth) {
        return holidaysForCurrentMonth.stream()
                .map(holiday -> new CalendarDTO(
                        null,    // ID는 null, 공휴일 데이터에는 없으므로
                        null,       // userId는 필요 없음
                        null,       // projectId는 필요 없음
                        holiday.getDateName(),  // 공휴일 이름
                        holiday.getLocdate(),   // 시작일 = 공휴일 날짜
                        holiday.getLocdate(),   // 종료일 = 공휴일 날짜
                        null,   // 시작 시간은 null
                        null,   // 종료 시간은 null
                        true    // 공휴일 여부를 true로 표시
                ))
                .collect(Collectors.toList());
    }
}

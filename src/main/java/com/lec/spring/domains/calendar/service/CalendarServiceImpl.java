package com.lec.spring.domains.calendar.service;

import com.lec.spring.domains.calendar.dto.CalendarDTO;
import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import com.lec.spring.domains.calendar.entity.Calendar;
import com.lec.spring.domains.calendar.repository.CalendarRepository;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.user.entity.User;
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
    private final ProjectRepository projectRepository;

    // 개인 일정 페이지에서 개인 일정 + 공휴일 + 본인이 소속된 모든 팀 일정 보여주기
    // 팀은 여러 개 가능. 하지만 팀 일정은 해당 페이지에서 수정 및 삭제 불가
    @Override
    @Transactional(readOnly = true)
    public List<CalendarDTO> getUserCalendar(Long userId, List<Long> projectIds) {
        // 본인 개인 일정 + 본인이 속한 모든 팀 일정 조회 (QueryDSL 적용)
        List<CalendarDTO> userCalendar = calendarRepository.findUserCalendar(userId, projectIds);

        // 공휴일 추가
        List<CalendarDTO> holidays = convertHolidaysToCalendarDTOs(holidaysService.getHolidaysForCurrentMonth());
        userCalendar.addAll(holidays);

        return userCalendar;
    }

    // 팀 일정 페이지에서 공휴일 + 해당 팀 일정 보여주기
    // 개인적인 일정들은 여기에 보이지 않음. 해당 팀에서 작성한 것만 보임
    @Override
    @Transactional(readOnly = true)
    public List<CalendarDTO> getProjectCalendar(Long projectId) {
        // 프로젝트 일정 조회
        List<CalendarDTO> projectCalendar = calendarRepository.findProjectCalendar(projectId);

        // 공휴일 추가
        List<CalendarDTO> holidays = convertHolidaysToCalendarDTOs(holidaysService.getHolidaysForCurrentMonth());
        projectCalendar.addAll(holidays);

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
    public CalendarDTO addProjectEvent(Long projectId, Long userId, CalendarDTO calendarDTO) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Calendar calendar = Calendar.builder()
                .project(project)
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
                savedCalendar.getProject().getId(),
                savedCalendar.getContent(),
                savedCalendar.getStartDate(),
                savedCalendar.getEndDate(),
                savedCalendar.getStartTime(),
                savedCalendar.getEndTime(),
                false
        );
    }

    // 팀의 해당하는 일정 상세보기
    @Override
    public CalendarDTO detailProjectEvent(Long projectId, Long calendarId) {
        // Optional을 사용하여 일정 조회
        Calendar calendar = calendarRepository.findByProjectIdAndId(projectId, calendarId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        return new CalendarDTO(
                calendar.getId(),
                calendar.getUser() != null ? calendar.getUser().getId() : null,
                calendar.getProject().getId(),
                calendar.getContent(),
                calendar.getStartDate(),
                calendar.getEndDate(),
                calendar.getStartTime(),
                calendar.getEndTime(),
                false
        );
    }

    // 팀 일정 페이지에서 팀 일정 수정 -> 팀 멤버라면 누구든 가능
    @Override
    public CalendarDTO updateProjectEvent(Long projectId, Long userId, Long calendarId, CalendarDTO calendarDTO) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));

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
                updatedCalendar.getUser().getId(),
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
    public int deleteProjectEvent(Long projectId, Long userId, Long calendarId) {
        Optional<Calendar> calendarOpt = calendarRepository.findById(calendarId);

        if (calendarOpt.isPresent()) {
            Calendar calendar = calendarOpt.get();

            calendarRepository.delete(calendar);
            return 1;
        }
        return 0;
    }

    // 개인 일정 상세보기
    @Override
    public CalendarDTO detailCalendar(Long calendarId) {
        // 일정 ID에 해당하는 일정 조회
        Calendar calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));

        // 필요한 정보만을 CalendarDTO로 변환하여 반환
        return new CalendarDTO(
                calendar.getId(),
                calendar.getUser().getId(),
                null,
                calendar.getContent(),
                calendar.getStartDate(),
                calendar.getEndDate(),
                calendar.getStartTime(),
                calendar.getEndTime(),
                false
        );
    }

    // 공휴일 데이터를 CalendarDTO로 변환
    private List<CalendarDTO> convertHolidaysToCalendarDTOs(List<HolidaysDTO> holidaysForCurrentMonth) {
        return holidaysForCurrentMonth.stream()
                .map(holiday -> new CalendarDTO(
                        null,
                        null,
                        null,
                        holiday.getDateName(),
                        holiday.getLocdate(),
                        holiday.getLocdate(),
                        null,
                        null,
                        true
                ))
                .collect(Collectors.toList());
    }
}

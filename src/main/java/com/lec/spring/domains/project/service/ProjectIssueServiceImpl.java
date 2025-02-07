package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.dto.ProjectIssueDTO;
import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.entity.ProjectIssuePriority;
import com.lec.spring.domains.project.entity.ProjectIssueStatus;
import com.lec.spring.domains.project.repository.ProjectIssueRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectIssueServiceImpl implements ProjectIssueService {

    private final ProjectIssueRepository projectIssueRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    // 이슈 작성
    @Override
    public ProjectIssue save(Long projectId, ProjectIssueDTO projectIssueDTO) {
        projectIssueDTO.setManager(userRepository.findById(projectIssueDTO.getManagerId()).get());
        projectIssueDTO.setWriter(userRepository.findById(projectIssueDTO.getWriterId()).get());
        System.out.println("managerId: " + userRepository.findById(projectIssueDTO.getManagerId()).get());
        System.out.println("writerId: " + userRepository.findById(projectIssueDTO.getWriterId()).get());
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));
        projectIssueDTO.setProject(project);

        // 내용 검증
        validateProjectIssue(projectIssueDTO);

        // 시작 날짜가 마감 날짜 이후일 경우 예외 처리
        if (projectIssueDTO.getStartline() != null && projectIssueDTO.getDeadline() != null
                && projectIssueDTO.getStartline().isAfter(projectIssueDTO.getDeadline())) {
            throw new IllegalArgumentException("시작 날짜는 마감 날짜 이후일 수 없습니다.");
        }

        // 담당자가 지정되지 않은 경우, 작성자를 기본 담당자로 설정
        if (projectIssueDTO.getManager() == null) {
            projectIssueDTO.setManager(projectIssueDTO.getWriter());
        }

        // User 객체 먼저 저장 (기본적으로 프로젝트 이슈의 담당자도 저장)
        if (projectIssueDTO.getManager() != null) {
            userRepository.save(projectIssueDTO.getManager()); // 담당자 저장
        }

        // 상태와 우선순위 변환
        projectIssueDTO.setStatus(projectIssueDTO.getStatus());
        projectIssueDTO.setPriority(projectIssueDTO.getPriority());

        return projectIssueRepository.save(projectIssueDTO); // 프로젝트 이슈 저장
    }

    // 프로젝트별 이슈 목록
    @Override
    public List<ProjectIssue> listByProjectId(Long projectId) {
        return projectIssueRepository.findByProjectIdSorted(projectId);
    }

    // 이슈 수정
    @Override
    public int update(Long projectId, Long issueId, ProjectIssue updatedIssue) {
        var existingIssue = projectIssueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이슈입니다."));

        if (updatedIssue.getIssueName() != null) {
            existingIssue.setIssueName(updatedIssue.getIssueName());
        }
        if (updatedIssue.getStatus() != null) {
            existingIssue.setStatus(updatedIssue.getStatus()); // 상태 변환
        }
        if (updatedIssue.getPriority() != null) {
            existingIssue.setPriority(updatedIssue.getPriority()); // 우선순위 변환
        }
        if (updatedIssue.getStartline() != null && updatedIssue.getDeadline() != null) {
            if (updatedIssue.getStartline().isAfter(updatedIssue.getDeadline())) {
                throw new IllegalArgumentException("시작 날짜는 마감 날짜 이후일 수 없습니다.");
            }
            existingIssue.setStartline(updatedIssue.getStartline());
            existingIssue.setDeadline(updatedIssue.getDeadline());
        }
        if (updatedIssue.getManager() != null) {
            existingIssue.setManager(updatedIssue.getManager());
        }

        return 1;
    }

    // 이슈 삭제 -> 다중 선택 가능
    @Override
    public int deleteByIds(List<Long> issueIds) {
        if (issueIds == null || issueIds.isEmpty()) {
            return 0;
        }

        long count = projectIssueRepository.countByIdIn(issueIds);
        if (count == 0) {
            return 0;
        }

        projectIssueRepository.deleteAllById(issueIds);
        return (int) count;
    }

    // 이슈 정보 검증
    private void validateProjectIssue(ProjectIssue projectIssue) {
        if (projectIssue.getIssueName() == null || projectIssue.getIssueName().isEmpty()) {
            throw new IllegalArgumentException("작업명을 작성해주세요.");
        }
        if (projectIssue.getManager() == null) {
            throw new IllegalArgumentException("담당자를 지정해주세요.");
        }
        if (projectIssue.getStatus() == null) {
            throw new IllegalArgumentException("상태를 선택해주세요.");
        }
        if (projectIssue.getPriority() == null) {
            throw new IllegalArgumentException("우선 순위를 선택해주세요.");
        }
        if (projectIssue.getStartline() == null) {
            throw new IllegalArgumentException("시작 날짜를 지정해주세요.");
        }
        if (projectIssue.getDeadline() == null) {
            throw new IllegalArgumentException("마감 날짜를 지정해주세요.");
        }
    }

//    // 상태 변환
//    private ProjectIssueStatus convertStatus(String status) {
//        switch (status) {
//            case "진행중":
//                return ProjectIssueStatus.INPROGRESS;
//            case "완료":
//                return ProjectIssueStatus.COMPLETE;
//            case "시작 안함":
//                return ProjectIssueStatus.YET;
//            default:
//                throw new IllegalArgumentException("잘못된 상태 값입니다.");
//        }
//    }

//    // 우선순위 변환 (우선순위가 문자열로 들어온 경우 처리)
//    private ProjectIssuePriority convertPriority(String priority) {
//        switch (priority) {
//            case "높음":
//                return ProjectIssuePriority.HIGH;
//            case "중간":
//                return ProjectIssuePriority.MIDDLE;
//            case "낮음":
//                return ProjectIssuePriority.LOW;
//            default:
//                throw new IllegalArgumentException("잘못된 우선순위 값입니다.");
//        }
//    }
}

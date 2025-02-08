package com.lec.spring.domains.project.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.lec.spring.domains.project.dto.ProjectIssueDTO;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.repository.ProjectIssueRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectIssueServiceImpl implements ProjectIssueService {

    private final ProjectIssueRepository projectIssueRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    // 이슈 작성
    @Override
    public ProjectIssue save(Long projectId, ProjectIssueDTO projectIssueDTO) {
        // 작성자 조회
        User writer = userRepository.findById(projectIssueDTO.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 작성자입니다."));

        // 담당자 조회 (없으면 작성자로 설정)
        User manager = userRepository.findById(projectIssueDTO.getManagerId())
                .orElse(writer);

        // 프로젝트 조회
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));

        // 시작 날짜가 마감 날짜 이후일 경우 예외 처리
        if (projectIssueDTO.getStartline() != null && projectIssueDTO.getDeadline() != null
                && projectIssueDTO.getStartline().isAfter(projectIssueDTO.getDeadline())) {
            throw new IllegalArgumentException("시작 날짜는 마감 날짜 이후일 수 없습니다.");
        }

        // 담당자가 지정되지 않은 경우, 작성자를 기본 담당자로 설정
        if (projectIssueDTO.getManagerName() == null) {
            projectIssueDTO.setManagerName(projectIssueDTO.getWriterName());
        }

        // 상태와 우선순위 변환
        projectIssueDTO.setStatus(projectIssueDTO.getStatus());
        projectIssueDTO.setPriority(projectIssueDTO.getPriority());

        // DTO -> 엔티티로 변환
        ProjectIssue projectIssue = projectIssueDTO.toEntity(project, writer, manager);

        return projectIssueRepository.save(projectIssue); // 저장 후 반환
    }

    // 프로젝트별 이슈 목록
    @Override
    public List<ProjectIssueDTO> listByProjectId(Long projectId) {
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

    // 특정 이슈 상세조회
    @Override
    public ProjectIssueDTO getIssueDetail(Long projectId, Long issueId) {
        // 프로젝트와 이슈를 조회
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        ProjectIssue projectIssue = projectIssueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        // 작성자 및 담당자 정보 가져오기
        Long writerId = projectIssue.getWriter().getId();
        String writerName = projectIssue.getWriter().getUsername();
        Long managerId = projectIssue.getManager() != null ? projectIssue.getManager().getId() : null;
        String managerName = projectIssue.getManager() != null ? projectIssue.getManager().getUsername() : null;

        // ProjectIssueDTO로 변환하여 반환
        return new ProjectIssueDTO(
                projectIssue.getId(),
                projectIssue.getIssueName(),
                projectIssue.getPriority(),
                projectIssue.getStatus(),
                projectIssue.getDeadline(),
                projectIssue.getStartline(),
                projectIssue.getCreateAt(),
                writerId,
                writerName,
                managerId,
                managerName,
                projectIssue.getProject().getId()
        );
    }

    // 다중 삭제
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

    // 개별 이슈 삭제
    @Override
    public boolean deleteById(Long issueId) {
        Optional<ProjectIssue> issue = projectIssueRepository.findById(issueId);
        if (issue.isPresent()) {
            projectIssueRepository.delete(issue.get());
            return true;  // 삭제 성공
        }
        return false;  // 해당 이슈가 존재하지 않음
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
}

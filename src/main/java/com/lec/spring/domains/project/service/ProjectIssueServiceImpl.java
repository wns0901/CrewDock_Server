package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.repository.ProjectIssueRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectIssueServiceImpl implements ProjectIssueService {

    private final ProjectIssueRepository projectIssueRepository;
    private final ProjectRepository projectRepository;

    // 이슈 작성
    @Override
    public ProjectIssue save(Long projectId, ProjectIssue projectIssue) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));
        projectIssue.setProject(project);

        // 내용 검증
        validateProjectIssue(projectIssue);

        // 시작 날짜가 마감 날짜 이후일 경우 예외 처리
        if (projectIssue.getStartline() != null && projectIssue.getDeadline() != null
                && projectIssue.getStartline().isAfter(projectIssue.getDeadline())) {
            throw new IllegalArgumentException("시작 날짜는 마감 날짜 이후일 수 없습니다.");
        }

        // 담당자가 지정되지 않은 경우, 작성자를 기본 담당자로 설정
        if (projectIssue.getManager() == null) {
            projectIssue.setManager(projectIssue.getWriter());
        }

        return projectIssueRepository.save(projectIssue);
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
            existingIssue.setStatus(updatedIssue.getStatus());
        }
        if (updatedIssue.getPriority() != null) {
            existingIssue.setPriority(updatedIssue.getPriority());
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

}

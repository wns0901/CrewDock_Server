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
    public ProjectIssueDTO save(Long projectId, ProjectIssueDTO projectIssueDTO) {
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

        // 저장 후 엔티티를 DTO로 변환하여 반환
        ProjectIssue savedIssue = projectIssueRepository.save(projectIssueDTO.toEntity(project, writer, manager));

        return ProjectIssueDTO.fromEntity(savedIssue); // 저장 후 반환
    }

    // 프로젝트별 이슈 목록
    @Override
    public List<ProjectIssueDTO> listByProjectId(Long projectId) {
        return projectIssueRepository.findByProjectIdSorted(projectId);
    }

    // 이슈 수정
    @Override
    public int update(Long projectId, Long issueId, ProjectIssueDTO updatedIssue) {
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

        // 새로운 담당자가 존재하는 경우 업데이트
        if (updatedIssue.getManagerId() != null) {
            User newManager = userRepository.findById(updatedIssue.getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 담당자입니다."));
            existingIssue.setManager(newManager);
        }

        projectIssueRepository.save(existingIssue); // 변경 사항 저장

        return 1;
    }

    // 특정 이슈 상세조회
    @Override
    public ProjectIssueDTO getIssueDetail(Long projectId, Long issueId) {
        // 프로젝트 확인
        projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        ProjectIssue projectIssue = projectIssueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        // DTO 변환 후 반환
        return ProjectIssueDTO.fromEntity(projectIssue);
    }

    // 다중 삭제
    @Override
    public int deleteByIds(Long projectId, List<Long> issueIds) {
        List<ProjectIssue> issuesToDelete = projectIssueRepository.findAllById(issueIds);
        if (!issuesToDelete.isEmpty()) {
            projectIssueRepository.deleteAll(issuesToDelete);  // 이슈들 삭제
            return issuesToDelete.size();  // 삭제된 이슈 수 반환
        }
        return 0; // 삭제된 이슈 없음
    }

    // 개별 삭제
    @Override
    public int deleteById(Long projectId, Long issueId) {
        Optional<ProjectIssue> issue = projectIssueRepository.findById(issueId);
        if (issue.isPresent()) {
            projectIssueRepository.delete(issue.get()); // 개별 이슈 삭제
            return 1; // 삭제된 이슈 수 1
        }
        return 0; // 삭제된 이슈 없음
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

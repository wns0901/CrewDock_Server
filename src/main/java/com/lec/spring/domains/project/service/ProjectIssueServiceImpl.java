package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.repository.ProjectIssueRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectIssueServiceImpl implements ProjectIssueService {

    private final ProjectIssueRepository projectIssueRepository;
    private final ProjectRepository projectRepository;

    public ProjectIssueServiceImpl(ProjectIssueRepository projectIssueRepository, ProjectRepository projectRepository) {
        this.projectIssueRepository = projectIssueRepository;
        this.projectRepository = projectRepository;
        System.out.println("ProjectIssueServiceImpl() 생성");
    }

    // 이슈 작성
    @Override
    public ProjectIssue save(Long projectId, ProjectIssue projectIssue) {
        // 프로젝트 확인
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));
        projectIssue.setProject(project);

        // 필수 정보 검증
        validateProjectIssue(projectIssue);

        // 시작 날짜와 마감 날짜 검증
        if (projectIssue.getStartline().isAfter(projectIssue.getDeadline())) {
            throw new IllegalArgumentException("시작 날짜는 마감 날짜 이후일 수 없습니다.");
        }

        // 생성 시간 설정
        projectIssue.setCreateAt(LocalDateTime.now());

        // 이슈 저장
        return projectIssueRepository.save(projectIssue);
    }

    // 프로젝트별 이슈 목록
    @Override
    public List<ProjectIssue> listByProjectId(Long projectId) {
        return projectIssueRepository.findByProjectId(projectId);
    }

    // 이슈 수정
    @Override
    public int update(Long projectId, Long issueId, ProjectIssue updatedIssue) {
        var existingIssue = projectIssueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이슈입니다."));

        // 필수 정보 검증
        validateProjectIssue(updatedIssue);

        // 수정된 정보 반영
        existingIssue.setIssueName(updatedIssue.getIssueName());
        existingIssue.setStatus(updatedIssue.getStatus());
        existingIssue.setPriority(updatedIssue.getPriority());
        existingIssue.setStartline(updatedIssue.getStartline());
        existingIssue.setDeadline(updatedIssue.getDeadline());
        existingIssue.setManager(updatedIssue.getManager());

        // 저장
        projectIssueRepository.save(existingIssue);
        return 1;
    }

    // 이슈 삭제
    @Override
    public int deleteById(Long issueId) {
        if (projectIssueRepository.existsById(issueId)) {
            projectIssueRepository.deleteById(issueId);
            return 1;
        }
        return 0;
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

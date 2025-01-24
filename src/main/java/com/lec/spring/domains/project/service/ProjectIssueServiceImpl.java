package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.repository.ProjectIssueRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectIssueServiceImpl implements ProjectIssueService {

    private final ProjectIssueRepository projectIssueRepository;
    private final ProjectRepository projectRepository;

    public ProjectIssueServiceImpl(ProjectIssueRepository projectIssueRepository, ProjectRepository projectRepository) {
        this.projectIssueRepository = projectIssueRepository;
        this.projectRepository = projectRepository;
        System.out.println("ProjectIssueService() 생성");
    }

    // 이슈 작성
    @Override
    public ProjectIssue save(ProjectIssue projectIssue) {
        // 프로젝트 확인
        if (projectIssue.getProject() == null || projectIssue.getProject().getId() == null) {
            throw new IllegalArgumentException("프로젝트 정보가 필요합니다.");
        }
        projectRepository.findById(projectIssue.getProject().getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));

        // 필수 정보 검증
        validateProjectIssue(projectIssue);

        // 날짜 지정 확인
        // 시작 날짜가 마감 날짜보다 전
        // 마감 날짜가 시작 날짜 당일부터 그 이후까지


        // 이슈 작성(저장)
        return projectIssueRepository.save(projectIssue);
    }

    // 이슈 정보 검증
    public void validateProjectIssue(ProjectIssue projectIssue) {
        // 필수 정보 확인
        if(projectIssue.getIssueName() == null || projectIssue.getIssueName().isEmpty()) {
            throw new IllegalArgumentException("작업명을 작성해주세요.");
        } else if (projectIssue.getManager() == null) {
            throw new IllegalArgumentException("담당자를 지정해주세요.");
        } else if(projectIssue.getStatus() == null) {
            throw new IllegalArgumentException("상태를 선택해주세요.");
        } else if(projectIssue.getPriority() == null){
            throw new IllegalArgumentException("우선 순위를 선택해주세요.");
        } else if(projectIssue.getStartline() == null) {
            throw new IllegalArgumentException("시작 날짜를 지정해주세요");
        } else if(projectIssue.getDeadline() == null) {
            throw new IllegalArgumentException("마감 날짜를 지정해주세요");
        }
    }

    // 프로젝트 이슈 목록
    @Override
    public List<ProjectIssue> list() {
        return projectIssueRepository.findAll();
    }

    // 이슈 수정
    @Override
    public int update(ProjectIssue projectIssue) {

        // 이슈 읽어오기
        ProjectIssue issue = projectIssueRepository.findById(projectIssue.getId()).orElse(null);

        // 수정된 내용으로 update
        if(issue != null) {
            issue.setIssueName(projectIssue.getIssueName());
            issue.setStatus(projectIssue.getStatus());
            issue.setPriority(projectIssue.getPriority());
            issue.setStartline(projectIssue.getStartline());
            issue.setDeadline(projectIssue.getDeadline());

            projectIssueRepository.save(issue);
        }
        return 1;
    }

    // 이슈 삭제
    @Override
    public int deleteById(Long id) {

        int result = 0;

        // 존재하는 데이터인지 확인
        ProjectIssue projectIssue = projectIssueRepository.findById(id).orElse(null);

        // 이슈 삭제
        if(projectIssue != null) {
            projectIssueRepository.delete(projectIssue);
            result = 1;
        }

        return result;
    }

}

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
        // 현재 팀 정보 확인
        // TODO

        // 이슈 작성
        // TODO
        return null;
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

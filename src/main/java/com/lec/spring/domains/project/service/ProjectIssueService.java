package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.ProjectIssue;

import java.util.List;

public interface ProjectIssueService {

    // 이슈 작성 (작업명, 담당자, 상태, 우선순위, 타임라인)
    ProjectIssue save(ProjectIssue projectIssue);

    // 해당 프로젝트의 모든 이슈 목록 출력
    List<ProjectIssue> list();

    // 특정 이슈 수정하기 (작업명, 담당자, 상태, 우선순위, 타임라인)
    int update(ProjectIssue projectIssue);

    // 특정 이슈 삭제하기 (다중 선택 가능)
    int deleteById(Long id);
}

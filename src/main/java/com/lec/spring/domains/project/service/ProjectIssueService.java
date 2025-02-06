package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.dto.ProjectIssueDTO;
import com.lec.spring.domains.project.entity.ProjectIssue;

import java.util.List;

public interface ProjectIssueService {

    // 이슈 작성 (작업명, 담당자, 상태, 우선순위, 타임라인)
    ProjectIssue save(Long projectId, ProjectIssue projectIssue);

    // 해당 프로젝트의 모든 이슈 목록 출력
    List<ProjectIssueDTO> listByProjectId(Long projectId);

    // 특정 이슈 수정하기 (작업명, 담당자, 상태, 우선순위, 타임라인)
    int update(Long projectId, Long issueId, ProjectIssue updatedIssue);

    // 특정 이슈 삭제하기 (다중 선택 가능)
    int deleteByIds(List<Long> issueIds);

}

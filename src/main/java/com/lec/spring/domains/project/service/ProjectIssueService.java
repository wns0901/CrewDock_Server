package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.dto.ProjectIssueDTO;
import com.lec.spring.domains.project.entity.ProjectIssue;

import java.util.List;

public interface ProjectIssueService {

    // 이슈 작성 (작업명, 담당자, 상태, 우선순위, 타임라인)
    ProjectIssueDTO save(Long projectId, ProjectIssueDTO projectIssueDTO);

    // 해당 프로젝트의 모든 이슈 목록 출력
    List<ProjectIssueDTO> listByProjectId(Long projectId);

    // 특정 이슈 수정하기 (작업명, 담당자, 상태, 우선순위, 타임라인)
    int update(Long projectId, Long issueId, ProjectIssue updatedIssue);

    // 특정 이슈 상세 조회
    ProjectIssueDTO getIssueDetail (Long projectId, Long issueId);

    // 특정 이슈들 삭제하기 -> 다중 삭제 기능
    int deleteByIds(Long projectId, List<Long> issueIds);

    // 특정 이슈 삭제 -> 개별 삭제
    int deleteById(Long projectId, Long issueId);


}

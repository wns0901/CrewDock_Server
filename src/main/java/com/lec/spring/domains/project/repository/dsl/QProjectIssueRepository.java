package com.lec.spring.domains.project.repository.dsl;

import com.lec.spring.domains.project.dto.ProjectIssueDTO;
import com.lec.spring.domains.project.entity.Project;

import java.util.List;
import java.util.Optional;

public interface QProjectIssueRepository {
    // 프로젝트 정렬 (마감일 > 우선 순위 > 상태)
    List<ProjectIssueDTO> findByProjectIdSorted(Long projectId);

}

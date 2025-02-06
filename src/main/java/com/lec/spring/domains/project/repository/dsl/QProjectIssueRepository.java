package com.lec.spring.domains.project.repository.dsl;

import com.lec.spring.domains.project.dto.ProjectIssueDTO;
import com.lec.spring.domains.project.entity.ProjectIssue;
import com.lec.spring.domains.project.entity.ProjectIssuePriority;
import com.lec.spring.domains.project.entity.ProjectIssueStatus;
import com.lec.spring.domains.user.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface QProjectIssueRepository {
    // 프로젝트 정렬 (마감일 > 우선 순위 > 상태)
    List<ProjectIssueDTO> findByProjectIdSorted(Long projectId);
}

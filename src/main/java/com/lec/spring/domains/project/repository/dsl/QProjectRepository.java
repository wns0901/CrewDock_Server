package com.lec.spring.domains.project.repository.dsl;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectMemberAuthirity;

import java.util.List;

public interface QProjectRepository {

    List<Project> findProjectsByUserIdAndAuthorities(Long userId, List<Long> projectIds, List<ProjectMemberAuthirity> authorities, int row);

    List<Project> findRecruitmentProjectsByUserId(Long userId);

}

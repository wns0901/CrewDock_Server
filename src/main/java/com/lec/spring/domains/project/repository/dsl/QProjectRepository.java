package com.lec.spring.domains.project.repository.dsl;

import com.lec.spring.domains.project.entity.Project;

import java.util.List;

public interface QProjectRepository {

    List<Project> findProjectsByUserIdAndAuthorityWithStacksQueryDSL(Long userId);
    List<Project> findProjectsByUserIdAndAuthorities(Long userId,  int row);
    List<Project> findRecruitmentProjectsByUserId(Long userId);
}

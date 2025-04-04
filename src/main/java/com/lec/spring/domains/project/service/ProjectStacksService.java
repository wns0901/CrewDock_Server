package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.ProjectStacks;

import java.util.List;

public interface ProjectStacksService {

    //프로젝트 id 기반 스택 조회
    List<ProjectStacks> findByProjectId (Long projectId);

    List<Long> findProjectsByStack(String stackName);

    boolean existsByProjectId(Long projectId);
}

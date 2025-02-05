package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {


    List<ProjectDTO> getUserProjectsWithLimitAndStacks(Long userId, int row);

    List<ProjectDTO> getUserProjectsWithStacks(Long userId);

    List<ProjectDTO> getUserRecruitmentProjects(Long userId);

    ProjectDTO createProject(ProjectDTO projectDTO);
}

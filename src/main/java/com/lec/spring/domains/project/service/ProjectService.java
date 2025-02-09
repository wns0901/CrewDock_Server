package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.dto.ProjectCreatDTO;
import com.lec.spring.domains.project.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {


    List<ProjectDTO> getUserProjectsWithLimitAndStacks(int row);
    List<ProjectDTO> getUserProjectsWithStacks();
    List<ProjectDTO> getUserRecruitmentProjects();
    ProjectDTO createProject(Long userId, ProjectCreatDTO projectCreateDTO);
}
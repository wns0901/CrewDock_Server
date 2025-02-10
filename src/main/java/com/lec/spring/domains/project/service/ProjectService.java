package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.dto.ProjectUpdateDTO;
import com.lec.spring.domains.project.entity.Project;

import java.util.Calendar;
import java.util.List;

public interface ProjectService {
    Project getProject(Long projectId);

    void updateProject(Long projectId, ProjectUpdateDTO updatedProject);
}

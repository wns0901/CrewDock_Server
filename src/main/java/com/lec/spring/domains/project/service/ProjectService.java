package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.Project;

import java.util.Calendar;
import java.util.List;

public interface ProjectService {
    Project getProject(Long projectId);

    void updateProject(Long projectId, Project updatedProject);

    // 스토리보드 13pg 때 사용함
    List<Project> getCaptainProjects(Long userId);
}

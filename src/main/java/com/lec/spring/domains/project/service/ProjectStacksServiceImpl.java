package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.ProjectStacks;
import com.lec.spring.domains.project.repository.ProjectStacksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectStacksServiceImpl implements ProjectStacksService {

    private final ProjectStacksRepository projectStacksRepository;

    public ProjectStacksServiceImpl(ProjectStacksRepository projectStacksRepository) {
        this.projectStacksRepository = projectStacksRepository;
    }

    @Override
    public List<ProjectStacks> findByProjectId(Long projectId) {
        //프로젝트 id 기반 스택 조회
        return projectStacksRepository.findByProjectId(projectId);
    }

    // 특정 스택을 사용하는 프로젝트 ID 리스트 조회 - 필터옵션
    @Override
    public List<Long> findProjectsByStack(String stackName) {
        return projectStacksRepository.findProjectsByStack(stackName);
    }

    @Override
    public boolean existsByProjectId(Long projectId) {
        return projectStacksRepository.existsByProjectId(projectId);
    }

}

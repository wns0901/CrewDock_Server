package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.dto.ProjectDTO;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;


    public List<ProjectDTO> getUserProjectsWithStacks(Long userId) {
        return projectRepository.findProjectsByUserIdAndAuthorityWithStacksQueryDSL(userId, Arrays.asList("CREW", "CAPTAIN"))
                .stream().map(ProjectDTO::fromEntity).collect(Collectors.toList());
    }

    // CREW, CAPTAIN 권한을 가진 프로젝트 반환 (row 제한 있음)
    public List<ProjectDTO> getUserProjectsWithLimitAndStacks(Long userId, int row) {
        List<Project> projects = projectRepository.findProjectsByUserIdAndAuthorities(userId, Arrays.asList("CREW", "CAPTAIN"), row);
        return projects.stream().map(ProjectDTO::fromEntity).collect(Collectors.toList());
    }

    // CREW, WAITING 권한을 가진 프로젝트 반환
    @Override
    public List<ProjectDTO> getUserRecruitmentProjects(Long userId) {
        return projectRepository.findRecruitmentProjectsByUserId(userId, Arrays.asList("CREW", "WAITING"))
                .stream().map(ProjectDTO::fromEntity).collect(Collectors.toList());
    }
}

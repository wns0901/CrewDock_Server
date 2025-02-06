package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public Project getProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("없는 프로젝트"));
    }

    @Override
    @Transactional
    public void updateProject(Long projectId, Project updatedProject) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트없음"));

        if (updatedProject.getName() != null)  project.setName(updatedProject.getName());
        if (updatedProject.getPeriod() != null) project.setPeriod(updatedProject.getPeriod());
        if (updatedProject.getStartDate() != null) project.setStartDate(updatedProject.getStartDate());
        if (updatedProject.getStatus() != null) project.setStatus(updatedProject.getStatus());
        if (updatedProject.getGithubUrl1() != null) project.setGithubUrl1(updatedProject.getGithubUrl1());
        if (updatedProject.getGithubUrl2() != null) project.setGithubUrl2(updatedProject.getGithubUrl2());
        if (updatedProject.getDesignUrl() != null)  project.setDesignUrl(updatedProject.getDesignUrl());

        if (updatedProject.getImgUrl() != null) project.setImgUrl(updatedProject.getImgUrl());
        // TODO: 이미지 저장로직

        if (updatedProject.getIntroduction() != null) project.setIntroduction(updatedProject.getIntroduction());

        if (updatedProject.getStacks() != null && !updatedProject.getStacks().isEmpty())  project.setStacks(updatedProject.getStacks());
        projectRepository.save(project);

    }

    public List<Project> getCaptainProjects(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        return projectRepository.findAllByCaptainUser(user);
    }

}

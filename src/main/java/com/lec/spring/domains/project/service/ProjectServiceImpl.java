package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.dto.ProjectDTO;
import com.lec.spring.domains.project.dto.ProjectStacksDTO;
import com.lec.spring.domains.project.entity.*;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.project.repository.ProjectStacksRepository;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.repository.StackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final StackRepository stackRepository;
    private final ProjectStacksRepository projectStacksRepository;
    private final ProjectMemberRepository projectMemberRepository;


    public List<ProjectDTO> getUserProjectsWithStacks(Long userId) {
        return projectRepository.findProjectsByUserIdAndAuthorityWithStacksQueryDSL(userId)
                .stream().map(ProjectDTO::fromEntity).collect(Collectors.toList());
    }

    // CREW, CAPTAIN 권한을 가진 프로젝트 반환 (row 제한 있음)
    public List<ProjectDTO> getUserProjectsWithLimitAndStacks(Long userId, int row) {
        List<Project> projects = projectRepository.findProjectsByUserIdAndAuthorities(userId,  row);
        return projects.stream().map(ProjectDTO::fromEntity).collect(Collectors.toList());
    }

    // CREW, WAITING 권한을 가진 프로젝트 반환
    @Override
    public List<ProjectDTO> getUserRecruitmentProjects(Long userId) {
        return projectRepository.findRecruitmentProjectsByUserId(userId)
                .stream().map(ProjectDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        // ProjectDTO를 Project 엔티티로 변환
        Project project = ProjectDTO.toEntity(projectDTO);

        // 프로젝트 저장
        Project savedProject = projectRepository.save(project);

        // stackIds로 스택 정보 조회 (ProjectDTO에서 stackIds를 가져옴)
        List<Long> stackIds = projectDTO.getStacks().stream()
                .map(ProjectStacksDTO::getId) // ProjectStacksDTO에서 stackId를 추출
                .collect(Collectors.toList());

        // stackIds로 Stack 엔티티들 조회
        List<Stack> stacks = stackRepository.findAllById(stackIds);

        // ProjectStacks 객체 생성 후 저장
        for (Stack stack : stacks) {
            ProjectStacks projectStacks = new ProjectStacks(null, stack, savedProject.getId());
            projectStacksRepository.save(projectStacks);
        }

        // 프로젝트와 사용자 연결 (CAPTAIN 권한 부여)
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(savedProject);
        projectMember.setUserId(userId);  // 현재 로그인한 userId
        projectMember.setAuthority(ProjectMemberAuthirity.CAPTAIN);  // CAPTAIN 권한 부여
        projectMember.setStatus(ProjectMemberStatus.APPROVE);


        projectMemberRepository.save(projectMember);  // ProjectMember 저장

        // 저장된 프로젝트를 다시 DTO로 변환하여 반환
        return ProjectDTO.fromEntity(savedProject);
    }
}

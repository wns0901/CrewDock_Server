package com.lec.spring.domains.project.service;

import com.lec.spring.domains.portfolio.service.PortfolioService;
import com.lec.spring.domains.project.dto.ProjectCreatDTO;
import com.lec.spring.domains.project.dto.ProjectDTO;
import com.lec.spring.domains.project.entity.*;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.project.repository.ProjectStacksRepository;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.repository.StackRepository;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import com.lec.spring.global.common.entity.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final StackRepository stackRepository;
    private final ProjectStacksRepository projectStacksRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final PortfolioService portfolioService;


    @Override
    public ProjectDTO createProject(Long userId, ProjectCreatDTO projectCreateDTO) {

        Project project = Project.builder()
                .name(projectCreateDTO.getName())
                .period(projectCreateDTO.getPeriod())
                .startDate(projectCreateDTO.getStartDate())
                .status(ProjectStatus.BOARDING)  // 기본값 설정
                .build();

        Project savedProject = projectRepository.save(project);


        List<Stack> stacks = stackRepository.findAllById(projectCreateDTO.getStacks());
        List<ProjectStacks> projectStacksList = stacks.stream()
                .map(stack -> new ProjectStacks(null, stack, savedProject.getId()))
                .toList();

        projectStacksRepository.saveAll(projectStacksList);


        ProjectMember projectMember = ProjectMember.builder()
                .project(savedProject)
                .userId(userId)
                .authority(ProjectMemberAuthirity.CAPTAIN)
                .status(ProjectMemberStatus.APPROVE)
                .position(Position.valueOf(projectCreateDTO.getPosition()))
                .build();

        projectMemberRepository.save(projectMember);


        portfolioService.createPortfolioForProject(savedProject, userId, stacks);

        return ProjectDTO.fromEntityWithMember(savedProject, projectMember);
    }


    @Override
    public List<ProjectDTO> getUserProjectsWithStacks() {
        Long loggedInUserId = getLoggedInUserId();
        List<Long> projectIds = new ArrayList<>();
        List<ProjectMemberAuthirity> authorities = new ArrayList<>(extractProjectAuthorities(loggedInUserId, projectIds));

        authorities.removeIf(auth -> auth != ProjectMemberAuthirity.CAPTAIN && auth != ProjectMemberAuthirity.CREW);
        if (authorities.isEmpty()) return new ArrayList<>();

        List<Project> projects = projectRepository.findProjectsByUserIdAndAuthorities(loggedInUserId, projectIds, authorities, 0);


        return projects.stream()
                .map(project -> {
                    ProjectMember projectMember = projectMemberRepository.findByProjectAndUserId(project, loggedInUserId);
                    return ProjectDTO.fromEntityWithMember(project, projectMember);
                })
                .toList();
    }


    @Override
    public List<ProjectDTO> getUserProjectsWithLimitAndStacks(int row) {
        Long loggedInUserId = getLoggedInUserId();
        List<Long> projectIds = new ArrayList<>();
        List<ProjectMemberAuthirity> authorities = new ArrayList<>(extractProjectAuthorities(loggedInUserId, projectIds));



        if (authorities.isEmpty()) return new ArrayList<>();


        List<Project> projects = projectRepository.findProjectsByUserIdAndAuthorities(loggedInUserId, projectIds, authorities, row);


        return projects.stream()
                .map(project -> {
                    ProjectMember projectMember = projectMemberRepository.findByProjectAndUserId(project, loggedInUserId);
                    return ProjectDTO.fromEntityWithMember(project, projectMember);
                })
                .toList();
    }




    @Override
    public List<ProjectDTO> getUserRecruitmentProjects() {
        Long loggedInUserId = getLoggedInUserId();

        return projectRepository.findRecruitmentProjectsByUserId(loggedInUserId)
                .stream()
                .map(ProjectDTO::fromEntity)
                .toList();
    }


    private Long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("사용자가 인증되지 않았습니다.");
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다: " + username);
        }

        return user.getId();
    }


    private List<ProjectMemberAuthirity> extractProjectAuthorities(Long userId, List<Long> projectIds) {
        List<ProjectMember> userProjects = projectMemberRepository.findByUserId(userId);

        if (userProjects.isEmpty()) return new ArrayList<>();

        List<ProjectMemberAuthirity> authorities = new ArrayList<>();
        for (ProjectMember member : userProjects) {
            projectIds.add(member.getProject().getId());
            authorities.add(member.getAuthority());
        }


        authorities = authorities.stream()
                .filter(auth -> auth == ProjectMemberAuthirity.CAPTAIN || auth == ProjectMemberAuthirity.CREW)
                .collect(Collectors.toList());

        return authorities;
    }

}
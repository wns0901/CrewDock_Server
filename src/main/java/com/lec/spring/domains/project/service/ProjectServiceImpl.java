package com.lec.spring.domains.project.service;

import com.lec.spring.domains.chat.service.ChatRoomService;
import com.lec.spring.domains.portfolio.service.PortfolioService;
import com.lec.spring.domains.project.dto.ProjectCreatDTO;
import com.lec.spring.domains.project.dto.ProjectDTO;
import com.lec.spring.domains.project.dto.ProjectUpdateDTO;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.*;
import com.lec.spring.domains.project.entity.ProjectStacks;
import com.lec.spring.domains.project.entity.ProjectStatus;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.global.common.entity.Position;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.user.repository.UserRepository;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.project.repository.ProjectStacksRepository;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.repository.StackRepository;
import com.lec.spring.global.common.util.BucketDirectory;
import com.lec.spring.global.common.util.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final StackRepository stackRepository;
    private final ProjectStacksRepository projectStacksRepository;
    private final PortfolioService portfolioService;
    private final S3Service s3Service;
    private final ChatRoomService chatRoomService;


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



    @Override
    public Project getProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("없는 프로젝트"));
    }

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

        chatRoomService.createChatRoom(userId, savedProject.getName());

        portfolioService.createPortfolioForProject(savedProject, userId, stacks);

        return ProjectDTO.fromEntityWithMember(savedProject, projectMember);
    }


    @Override
    @Transactional
    public void updateProject(Long projectId, ProjectUpdateDTO updatedProject) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트 없음"));

        if (updatedProject.getName() != null) project.setName(updatedProject.getName());
        if (updatedProject.getPeriod() != null) project.setPeriod(updatedProject.getPeriod());
        if (updatedProject.getStartDate() != null) project.setStartDate(updatedProject.getStartDate());
        if (updatedProject.getStatus() != null)
            project.setStatus(ProjectStatus.valueOf(updatedProject.getStatus())); // Enum 변환
        if (updatedProject.getGithubUrl1() != null) project.setGithubUrl1(updatedProject.getGithubUrl1());
        if (updatedProject.getGithubUrl2() != null) project.setGithubUrl2(updatedProject.getGithubUrl2());
        if (updatedProject.getDesignUrl() != null) project.setDesignUrl(updatedProject.getDesignUrl());

        if(updatedProject.getFile() != null) {
            if(project.getImgUrl() != null) s3Service.deleteFile(project.getImgUrl());
            String imgUrl = s3Service.uploadImgFile(updatedProject.getFile(), BucketDirectory.PROJECTPROFILE);
            project.setImgUrl(imgUrl);
        }


        if (updatedProject.getIntroduction() != null) project.setIntroduction(updatedProject.getIntroduction());



        projectStacksRepository.deleteByProjectIdWithQuery(projectId);
        if (updatedProject.getStackIds() != null) {
            List<Long> newStackIds = updatedProject.getStackIds();
            System.out.println("새로운 스택 IDs: " + newStackIds);


            // 새로운 스택들을 ProjectStacks 객체로 변환하여 저장
            List<ProjectStacks> newProjectStacks = newStackIds.stream()
                    .map(stackId -> {
                        Stack stack = stackRepository.findById(stackId)
                                .orElseThrow(() -> new IllegalArgumentException("없는 스택 ID: " + stackId));

                        // ProjectStacks 객체 생성 시 id를 명시적으로 설정하지 않음 (자동으로 null 처리됨)
                        return ProjectStacks.builder()
                                .stack(stack)  // Stack 객체를 설정
                                .projectId(projectId)  // projectId를 연결
                                .build();
                    })
                    .collect(Collectors.toList());

            // 새로운 스택을 ProjectStacks에 저장
            projectStacksRepository.saveAll(newProjectStacks);
            System.out.println("새로운 스택: " + newProjectStacks);

        }


    }

    @Override
    public List<Project> getCaptainProjects(Long userId) {
        return List.of();
    }
}
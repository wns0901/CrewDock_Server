package com.lec.spring.domains.project.service;

import com.lec.spring.domains.project.dto.ProjectUpdateDTO;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectStacks;
import com.lec.spring.domains.project.entity.ProjectStatus;
import com.lec.spring.domains.project.repository.ProjectRepository;
import com.lec.spring.domains.project.repository.ProjectStacksRepository;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.stack.repository.StackRepository;
import com.lec.spring.global.common.util.BucketDirectory;
import com.lec.spring.global.common.util.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final StackRepository stackRepository;
    private final ProjectStacksRepository projectStacksRepository;
   private final S3Service s3Service;


    @Override
    public Project getProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("없는 프로젝트"));
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

}
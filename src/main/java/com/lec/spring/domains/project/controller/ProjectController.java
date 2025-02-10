package com.lec.spring.domains.project.controller;

import com.lec.spring.domains.project.dto.GitDataDTO;
import com.lec.spring.domains.project.dto.ProjectUpdateDTO;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.service.GitService;
import com.lec.spring.domains.project.service.ProjectService;
import com.lec.spring.domains.project.util.GitUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final GitService gitService;
    private final ProjectService projectService;

    // 깃 데이터
    @GetMapping("/{projectId}/githubs")
    public Mono<ResponseEntity<List<GitDataDTO>>> getGitData(@PathVariable Long projectId,
                                                             @RequestParam List<String> gitURL) {

        return gitService.getGitDataFromUrls(gitURL)
                .map(gitDataDTOList -> ResponseEntity.ok().body(gitDataDTOList))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // 프로젝트 정보 조회
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);
        return ResponseEntity.ok(project);
    }

    @PatchMapping
    public ResponseEntity<Void> updateProject(@RequestBody ProjectUpdateDTO updatedProject) {
        System.out.println("수정할 플젝정보" + updatedProject);
        projectService.updateProject(updatedProject.getId(), updatedProject);
        return ResponseEntity.noContent().build();
    }

}

